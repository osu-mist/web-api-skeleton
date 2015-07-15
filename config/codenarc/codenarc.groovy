/* CodeNarc RuleSet
   http://codenarc.sourceforge.net/codenarc-rule-index.html */

ruleset {
    description 'MIST Groovy RuleSet'

    /* class name starts with an uppercase letter and is followed by zero or
       more word characters or dollar signs */
    ClassName

    /* method name starts with a lowercase letter */
    MethodName

    /* package name consists only of lowercase letters and numbers, separated
       by periods */
    PackageName

    /* semicolons as line terminators are not required in Groovy */
    UnnecessarySemicolon
}
