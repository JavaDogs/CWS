#!/bin/bash
# -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
# WildFly Control Script
# -----------------------------------------------------------------------------
# Configuration settings, please only modify this section
# -----------------------------------------------------------------------------
readonly dbHost="localhost"
readonly dbPort="5432"
readonly dbUser="cws_user"
readonly dbPassword="cws"
readonly dbName="cws"
# Maximal file size for uploading to WildFly - 25 MB (25 * 1024 * 1024)
readonly maxPostSize="26214400"
readonly wildfly=${JBOSS_HOME}
# Hidden feature, if set this port is used to start JBoss/WildFly in debug mode
readonly debugPort=${DEBUG_PORT}

# Java & JBoss (WildFly) settings
export JAVA_OPTS="${JAVA_OPTS} -Xms1303m -Xmx1303m -Djava.net.preferIPv4Stack=true"
readonly proxy="${http_proxy:-}"
if [[ ${proxy} != "" ]]; then
    readonly tmp=${proxy//:[[:digit:]]*/}
    readonly host=${tmp:7}
    readonly port=${proxy//[^0-9]/}
    export JAVA_OPTS="${JAVA_OPTS} -Dhttp.proxyHost=${host} -Dhttp.proxyPort=${port} -Dhttps.proxyHost=${host} -Dhttps.proxyPort=${port}"
fi
# -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

# -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
# Run the Control Script
# -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
if [[ ${wildfly} == "" ]]; then
    echo "Script requires that the system variable $JBOSS_HOME is defined."
    echo
    exit
fi

# -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
# Run the Control Script
# -----------------------------------------------------------------------------
# Param1: JBOSS Client Parameters
# Return: True (0) or false (non-zero)
# -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
function runJBossCommand() {
    local parameter=$1

    ${wildfly}/bin/jboss-cli.sh --connect --controller=localhost --command="${parameter}" >/dev/null 2>&1
    return $?
}

action=${1}
if [[ ${action} == "configure" ]]; then
    if (runJBossCommand "read-attribute server-state"); then
        if [[ ! -e ${wildfly}/modules/org/postgresql/main/module.xml ]]; then
            echo "Configuring WildFly for CWS"
            mkdir -p "${wildfly}/modules/org/postgresql/main"
            cp "$(dirname "$0")/../lib/postgresql-42.2.8.jar" "${wildfly}/modules/org/postgresql/main"
            cp "$(dirname "$0")/../wildfly/module.xml" "${wildfly}/modules/org/postgresql/main"
            runJBossCommand "/subsystem=datasources/jdbc-driver=postgresql:add(driver-name=postgresql,driver-module-name=org.postgresql,driver-xa-datasource-class-name=org.postgresql.xa.PGXADataSource)"
            runJBossCommand "data-source add --name=cwsDS --driver-name=postgresql --jndi-name=java:/datasources/cwsDS --connection-url=jdbc:postgresql://${dbHost}:${dbPort}/${dbName} --user-name=${dbUser} --password=${dbPassword} --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000 --enabled=true"
            runJBossCommand "/subsystem=undertow/server=default-server/http-listener=default/:write-attribute(name=max-post-size,value=${maxPostSize})"
            runJBossCommand "/subsystem=logging/logger=io.javadog.cws:add"
            echo "Restarting WildFly ..."
            runJBossCommand "reload"
            echo "WildFly has been configured"
        else
            echo "WildFly have already been configured"
        fi
    else
        echo "WildFly is not running, please start WildFly before configuring it"
    fi
elif [[ ${action} == "status" ]]; then
    if (runJBossCommand "read-attribute server-state"); then
        if [[ $(runJBossCommand "deployment-info --name=cws.war") ]]; then
            echo "WildFly is running with CWS deployed"
        else
            echo "WildFly is running"
        fi
    else
        echo "WildFly is not running"
    fi
elif [[ ${action} == "start" ]]; then
    if (runJBossCommand "read-attribute server-state"); then
        echo "WildFly is already running"
    else
        echo "Starting WildFly ..."
        if [[ ${debugPort} == "" ]]; then
            echo "Starting WildFly in production mode"
            ${wildfly}/bin/standalone.sh -c standalone-full.xml -b 0.0.0.0 -Djboss.node.name=cws &
        else
            echo "Starting WildFly with Debug enabled at port $DEBUG_PORT"
            ${wildfly}/bin/standalone.sh -c standalone-full.xml -b 0.0.0.0 -Djboss.node.name=cws --debug "${debugPort}" &
        fi
    fi
elif [[ ${action} == "stop" ]]; then
    if (runJBossCommand "read-attribute server-state"); then
        echo "Stopping WildFly ..."
        runJBossCommand "shutdown"
    else
        echo "WildFly is not running"
    fi
elif [[ ${action} == "deploy" ]]; then
    if (runJBossCommand "read-attribute server-state"); then
        echo "Deploying CWS"
        runJBossCommand "deploy $(dirname $0)/../wildfly/cws.war --force"
    else
        echo "WildFly is not running"
    fi
elif [[ ${action} == "undeploy" ]]; then
    if (runJBossCommand "read-attribute server-state"); then
        if (runJBossCommand "deployment-info --name=cws.war"); then
            echo "Undeploying CWS"
            runJBossCommand "undeploy cws.war"
        else
            echo "CWS was not deployed"
        fi
    else
        echo "WildFly is not running"
    fi
elif [[ ${action} == "log" ]]; then
    tail -f "${wildfly}/standalone/log/server.log"
else
    echo "WildFly Control script"
    echo "Usage: $(basename "$0") [Action]"
    echo
    echo "  The Action must be one of the following:"
    echo "    configure Attempts to configure a CWS WildFly instance"
    echo "    start     Attempts to start a CWS WildFly instance"
    echo "    stop      Attempts to stop the running CWS WildFly instance"
    echo "    status    Check the current status of WildFly"
    echo "    deploy    Deploy the latest CWS snapshot to WildFly"
    echo "    undeploy  Undeploy the currently deployed CWS snapshot"
    echo "    log       Tail on the Server Log"
    echo
fi
