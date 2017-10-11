/*
 * =============================================================================
 * Copyright (c) 2016-2017, JavaDog.io
 * -----------------------------------------------------------------------------
 * Project: CWS (cws-api)
 * =============================================================================
 */
package io.javadog.cws.api.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>This Class contains checks for different fields that is used as part of
 * the input and output Objects. Reason for having this, is because it is
 * important that all data is 100% reliable when it is coming in so it can be
 * processed correctly without any errors occurring.</p>
 *
 * <p>Although all Classes and Fields are annotated with the necessary checks,
 * this is not a guarantee that the data will also correctly be passed through,
 * since different frameworks may choose to discard the Annotated requirements
 * or have flaws. Hence, this simple PoJo approach will act as a last line of
 * defense before data is being processed.</p>
 *
 * @author Kim Jensen
 * @since  CWS 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verifiable")
public abstract class Verifiable implements Serializable {

    /** {@link Constants#SERIAL_VERSION_UID}. */
    private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;
    private static final String PRE_VALUE = "The value for '";
    private static final String PATTERN_PART = "' is not matching the required pattern '";

    /**
     * Simple Validation method, which checks if the required values are usable
     * or not.
     *
     * @return Map with Fields and error information
     */
    public abstract Map<String, String> validate();

    protected static void checkNotNull(final Map<String, String> errors, final String field, final Object value, final String message) {
        if (value == null) {
            errors.put(field, message);
        }
    }

    protected static void checkNotEmpty(final Map<String, String> errors, final String field, final String value, final String message) {
        if ((value != null) && value.trim().isEmpty()) {
            errors.put(field, message);
        }
    }

    protected static void checkNotNullOrEmpty(final Map<String, String> errors, final String field, final String value, final String message) {
        if ((value == null) || value.trim().isEmpty()) {
            errors.put(field, message);
        }
    }

    protected static void checkNotTooLong(final Map<String, String> errors, final String field, final String value, final int maxLength, final String message) {
        if ((value != null) && (value.trim().length() > maxLength)) {
            errors.put(field, message);
        }
    }

    protected static void checkValidId(final Map<String, String> errors, final String field, final String value, final String message) {
        if (value != null) {
            final Pattern pattern = Pattern.compile(Constants.ID_PATTERN_REGEX);
            if (!pattern.matcher(value).matches()) {
                errors.put(field, message);
            }
        }
    }

    protected static void checkNotNullAndValidId(final Map<String, String> errors, final String field, final String value, final String message) {
        checkNotNull(errors, field, value, message);
        checkValidId(errors, field, value, message);
    }

    protected static void checkPattern(final Map<String, String> errors, final String field, final String value, final String regex, final String message) {
        if (value != null) {
            final Pattern pattern = Pattern.compile(regex);
            if (!pattern.matcher(value).matches()) {
                errors.put(field, message);
            }
        }
    }

    protected static <E extends Enum<?>> void checkContains(final Map<String, String> errors, final String field, final E value, final Collection<E> acceptable, final String message) {
        if ((value != null) && !acceptable.contains(value)) {
            errors.put(field, message);
        }
    }

    protected static void ensureNotNull(final String field, final Object value) {
        if (value == null) {
            throw new IllegalArgumentException(PRE_VALUE + field + "' may not be null.");
        }
    }

    protected void ensureNotNullEmptyOrTooLong(final String field, final String value, final int max) {
        if (value != null) {
            if (value.isEmpty()) {
                throw new IllegalArgumentException(PRE_VALUE + field + "' is empty.");
            } else if (value.length() > max) {
                throw new IllegalArgumentException(PRE_VALUE + field + "' is longer than the maximum length of " + max + '.');
            }
        }
    }

    protected static void ensureValidId(final String field, final String value) {
        if (value != null) {
            final Pattern pattern = Pattern.compile(Constants.ID_PATTERN_REGEX);
            if (!pattern.matcher(value).matches()) {
                throw new IllegalArgumentException(PRE_VALUE + field + PATTERN_PART + Constants.ID_PATTERN_REGEX + "'.");
            }
        }
    }

    protected static void ensureVerifiable(final String field, final Verifiable value) {
        if (value != null) {
            final Map<String, String> errors = value.validate();
            if (!errors.isEmpty()) {
                throw new IllegalArgumentException(PRE_VALUE + field + "' is not a Valid Object.");
            }
        }
    }

    protected void ensurePattern(final String field, final String value, final String regex) {
        if (value != null) {
            final Pattern pattern = Pattern.compile(regex);
            if (!pattern.matcher(value).matches()) {
                throw new IllegalArgumentException(PRE_VALUE + field + PATTERN_PART + regex + "'.");
            }
        }
    }

    protected static <E extends Enum<?>> void ensureValidEntry(final String field, final E value, final Collection<E> acceptable) {
        if (!acceptable.contains(value)) {
            throw new IllegalArgumentException(PRE_VALUE + field + "' is not allowed.");
        }
    }

    protected static void ensureNotEmptyOrTooLong(final String field, final String value, final int max) {
        if (value != null) {
            final int length = value.trim().length();
            if ((length < 1) || (length > max)) {
                throw new IllegalArgumentException(PRE_VALUE + field + "' is outside of the allowed boundaries.");
            }
        }
    }

    protected static void ensureValidRange(final String field, final int value, final int min, final int max) {
        if ((value < min) || (value > max)) {
            throw new IllegalArgumentException(PRE_VALUE + field + "' is outside of the allowed boundaries.");
        }
    }

    protected static void ensurePositiveNumber(final String field, final int value) {
        if (value < 1) {
            throw new IllegalArgumentException(PRE_VALUE + field + "' must be at least 1.");
        }
    }

    protected static void extendErrors(final Map<String, String> toExtend, final Map<String, String> toAppend, final String keyPrefix) {
        for (final Map.Entry<String, String> entry : toAppend.entrySet()) {
            toExtend.put(keyPrefix + entry.getKey(), entry.getValue());
        }
    }
}
