#!/usr/bin/env bash

# Following is a simple Script, using Bash to perform various simple operations
# against a CWS 1.1 instance.
#   The script uses a config file called ~/.cwsrc where user information and
# host information is stored.

# Script requires only wget & jq to function, without - exit, with error message
which jq >/dev/null || { echo "CWS Bash Client requires 'jq'"; exit 1; }
which wget >/dev/null || { echo "CWS Bash Client requires 'wget'"; exit 1; }


config="~/.cwsrc"
# Associative Array with the Bash CWS Settings from the configuration file
readonly declare -A settings
keyUrl="cws.url"
keyAccount="cws.account"
keyCredential="cws.credential"
keyToken="cws.token"
keyAuthCheck="cws.authentication.check"

bcreds=$(echo -n $creds | base64 -w 0)
wget -q -O- -S --header='Content-Type:application/json' --post-data "{\"accountName\":\"${account}\"\,"credential\":\"YWRtaW4=\"\}\" "http://localhost:8080/cws/authenticated"


# =============================================================================
# INTERNAL :: Creates a new CWS Settings File, will overwrite any existing file
# -----------------------------------------------------------------------------
# Params: Void
# Return: True (0)
# Output: Configuration File
# =============================================================================
function __cwsCreateSettingsFile() {
    {
        echo "## CWS Configuration File for Scripting"
        echo ""
        echo "# CWS Hostname, is the URL for CWS Instance to use, it must be"
        echo "# a complete HTTP/HTTPS based URL."
        echo "${keyUrl} = https://javadog.io/cws"
        echo ""
        echo "# Standard Account Credentials for communication, required when"
        echo "# a new Session Token is created for "
        echo "${keyAccount} = admin"
        echo "${keyCredential} = admin"
        echo ""
        echo "Please to not change the settings below, they are used by the"
        echo "script to perform internal checks"
        echo "${keyToken} ="
        echo "${keyAuthCheck} ="
    } >"${config}"

    return true
}

# =============================================================================
# INTERNAL :: Reads a specific setting Value from the CWS Settings
# -----------------------------------------------------------------------------
# Params: Key to read Value for
# Return: True (0)
# Output: Value for the given Key
# =============================================================================
function __cwsReadSettingKey() {
    key=$1
    tmp=$(grep "${key}" "${config}" | cut -d "=" -f 2)
    echo ${tmp// /}

    return true
}

# =============================================================================
# INTERNAL :: Read all the Settings from the CWS Settings File
# -----------------------------------------------------------------------------
# Params: Key to read Value for
# Return: True (0)
# Output: Void
# =============================================================================
function __cwsReadSettings() {
    if [[ ! -f "${config}" ]]; then
        __cwsCreateSettingsFile
    fi

    settings[${keyUrl}]=$(readSettingKey "${keyUrl}")
    settings[${keyAccount}]=$(readSettingKey "${keyAccount}")
    settings[${keyCredential}]=$(readSettingKey "${keyCredential}")
    settings[${keyToken}]=$(readSettingKey "${keyToken}")
    settings[${keyAuthCheck}]=$(readSettingKey "${keyAuthCheck}")

    return true
}

# =============================================================================
# INTERNAL :: Writes all the current Settings to the CWS Settings File
# -----------------------------------------------------------------------------
# Params: Void
# Return: True (0)
# Output: Void
# =============================================================================
function __cwsWriteSettings() {
    for key in "${!settings[@]}"; do
        sed -i "s/^${key}.*/${key} = ${settings[${key}]}" ${config}
    done

    return true
}

function __cwsReadTokenOrLogIn() {
    if [[  ]]; then

    else
    fi

    return true
}

function getCwsToken() {

}

function createCWSSession() {

}

function closeCWSSession() {

}

token=$(__cwsReadTokenOrLogIn)
closeCWSSession
