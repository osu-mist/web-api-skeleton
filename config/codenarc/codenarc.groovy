/* CodeNarc RuleSet
   http://codenarc.sourceforge.net/codenarc-rule-index.html */

ruleset {
    description 'MIST Groovy RuleSet'

    /* class name starts with a uppercase letter */
    ClassName

    /* variable name starts with a lowercase letter */
    VariableName

    /* method name starts with a lowercase letter */
    MethodName

    PackageName {
        description = "Verify that package name begins with 'edu.oregonstate.mist.'"
        regex = /^edu\.oregonstate\.mist\..*$/
        packageNameRequired = true
    }

    /* package name consists only of lowercase letters and numbers, separated
       by periods */
    PackageName

    /* semicolons as line terminators are not required in Groovy */
    UnnecessarySemicolon

    /* If a statement is the last line in a method or closure then you do not need to have the return keyword. */
    UnnecessaryReturnKeyword

    /* Checks that if statements use braces, even for a single statement. */
    IfStatementBraces

    /* Checks that else blocks use braces, even for a single statement. */
    ElseBlockBraces

    /* Checks the location of the opening brace ({) for if statements, including elseOnSameLineAsClosingBrace and elseOnSameLineAsOpeningBrace */
    BracesForIfElse {
        validateElse = true
    }

    /* Checks that for statements use braces, even for a single statement. */
    ForStatementBraces

    /* Checks that while statements use braces, even for a single statement. */
    WhileStatementBraces

    /* Makes sure there are no consecutive lines that are either blank or whitespace only. */
    ConsecutiveBlankLines

    /* Check that there is at least one space before each opening brace */
    SpaceBeforeOpeningBrace

    /* Check that there is at least one space after each closing brace */
    SpaceAfterClosingBrace

    /* Check that the opening brace for the method starts on the same line */
    BracesForMethod

    /* Check that there is at least one space (blank) or whitespace around each binary operator */
    SpaceAroundOperator

    /* Check that the opening brace for classes starts on the same line */
    BracesForClass

    /* Check that the opening brace for loops starts on the same line */
    BracesForForLoop

    /* Check that the opening brace for try statements starts on the same line */
    BracesForTryCatchFinally

    /* Checks the maximum length for each line of source code. Defaults is 120 characters maximum. */
    LineLength {
        description = 'Checks for number of characters, so lines that include tabs may not ' +
                      'exceed 100 characters.'
        length = 100

        priority = 3
    }
}
