package edu.oregonstate.mist.api

/**
 * Abstract class for replacing strings.
 */
abstract class Language {
    /**
     * Associative array of strings and their replacements.
     */
    private static Map replacement = [:]

    /**
     * Returns the string replacement if it exists, otherwise returns the original string.
     *
     * @param string
     * @return replacement string
     */
    public static String translate(String string) {
        replacement.get(string) ?: string
    }
}
