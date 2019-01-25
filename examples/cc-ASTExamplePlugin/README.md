From: Lea Hänsenberger <lhaensenberger@students.unibe.ch>
Date: 3 November 2008 15:48:02 GMT+01:00
To: Oscar Nierstrasz <oscar@iam.unibe.ch>
Subject: Re: eclipse refactoring engine

Hi Oscar,
I implemented a small plugin that renames all the fields and field accesses of a class by adding a '_' at the beginning of the original name. Attached you find the plugin.

When the plugin is installed you can right click on any java class that has at least one field and select 'RenameFields' -> 'Rename' in the popup menu. If the class is already open in an editor you immediately see the changes.

In the plugin itself the most important things happen in the astexampleplugin.action.ChangeAction and the astexampleplugin .ast.StackVisitor. In the astexampleplugin.action.ChangeAction.run() method the AST for every selected ICompilationUnit (Java class) is created and the AST then accepts the StackVisitor. Afterwards the changes made to the AST by the StackVisitor are recorded and applied to the original java class. In the StackVisitor only the vist methods for FieldDeclaration and FieldAccess are implemented and they just change the names of all the fields.

I've tested the plugin with eclipse 3.4 and Java 1.5 but it should also work with older eclipse versions and/or Java 6.

cheers,
Lea

