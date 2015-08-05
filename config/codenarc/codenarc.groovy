/* CodeNarc RuleSet
   http://codenarc.sourceforge.net/codenarc-rule-index.html */

ruleset {
    description 'MIST Groovy RuleSet'

    ClassName {
        description = 'Verify that class name starts with an uppercase letter followed by zero or ' +
                      'more word characters or dollar signs, warning if skeleton code is unchanged.'
        regex = '''(?x)
                   ^(
                     (?!(SkeletonApplication|Sample|SampleResource)$) # fail on skeleton class names
                     ([A-Z]\\w*$?)*                                   # codenarc default
                   )$'''
        priority = 3
    }

    /* method name starts with a lowercase letter */
    MethodName

    PackageName {
        description = 'Verify that package name begins with \'edu.oregonstate.mist.\' and consists ' +
                      'only of lowercase letters and numbers separated by periods, warning if ' +
                      'skeleton code is unchanged.'
        regex = '''(?x)
                   ^(
                     edu\\.oregonstate\\.mist # begin with edu.oregonstate.mist
                     (?!\\.webapiskeleton)    # fail on skeleton package name
                     (\\.[a-z0-9]+)*          # periods separate lowercase alphanumeric package names
                   )$'''
        packageNameRequired = true
        priority = 3
    }

    /* package name consists only of lowercase letters and numbers, separated
       by periods */
    PackageName

    /* semicolons as line terminators are not required in Groovy */
    UnnecessarySemicolon
}
