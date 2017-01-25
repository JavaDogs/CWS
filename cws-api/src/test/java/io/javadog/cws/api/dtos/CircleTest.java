package io.javadog.cws.api.dtos;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Kim Jensen
 * @since  CWS 1.0
 */
public class CircleTest {

    @Test
    public void testClass() {
        final String id = UUID.randomUUID().toString();
        final String name = "Circle Name";

        final Circle circle = new Circle();
        circle.setId(id);
        circle.setName(name);

        assertThat(circle.getId(), is(id));
        assertThat(circle.getName(), is(name));

        final Map<String, String> errors = circle.validate();
        assertThat(errors.size(), is(0));
    }

    @Test
    public void testEmptyObjectValidation() {
        final Circle circle = new Circle();

        final Map<String, String> errors = circle.validate();
        assertThat(errors.size(), is(1));
    }

    @Test
    public void testForcingInvalidData1() throws NoSuchFieldException, IllegalAccessException {
        final Circle circle = new Circle();
        final Field name = circle.getClass().getDeclaredField("name");
        name.setAccessible(true);
        name.set(circle, "");
        final Field id = circle.getClass().getDeclaredField("id");
        id.setAccessible(true);
        id.set(circle, "123");

        final Map<String, String> errors = circle.validate();
        assertThat(errors.size(), is(2));
    }

    @Test
    public void testForcingInvalidData2() throws NoSuchFieldException, IllegalAccessException {
        final Circle circle = new Circle();
        final Field name = circle.getClass().getDeclaredField("name");
        name.setAccessible(true);
        name.set(circle, "12345678901234567890123456789012345678901234567890123456789012345678901234567890");
        final Field id = circle.getClass().getDeclaredField("id");
        id.setAccessible(true);
        id.set(circle, "");

        final Map<String, String> errors = circle.validate();
        assertThat(errors.size(), is(2));
    }

    @Test
    public void testNullId() {
        final Circle circle = new Circle();
        circle.setId(null);

        assertThat(circle.getId(), is(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShortId() {
        final Circle circle = new Circle();
        circle.setId("short");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLongId() {
        final Circle circle = new Circle();
        circle.setId("1234567890123456789012345789012345678901234567890");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullName() {
        final Circle circle = new Circle();
        circle.setName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setTooLongName() {
        final Circle circle = new Circle();
        circle.setName("1234567890123456789012345678901234567891234567890123456789012345678901234567890");
    }
}
