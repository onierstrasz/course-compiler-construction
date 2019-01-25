
package astexampleplugin.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import astexampleplugin.ast.StackVisitor;


/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * @see IWorkbenchWindowActionDelegate
 */
public class ChangeAction
        implements IWorkbenchWindowActionDelegate {

    private IWorkbenchWindow       window;
    private List<ICompilationUnit> classes;


    /**
     * The constructor.
     */
    public ChangeAction() {
        this.classes = new ArrayList<ICompilationUnit>();
    }

    /**
     * The action has been activated. The argument of the method represents the
     * 'real' action sitting in the workbench UI.
     * @see IWorkbenchWindowActionDelegate#run
     */
    public void run( IAction action ) {
        for ( ICompilationUnit cu : this.classes ) {
            try {
                ASTParser parser = ASTParser.newParser( AST.JLS3 );
                parser.setKind( ASTParser.K_COMPILATION_UNIT );
                parser.setSource( cu );
                Document document;
                document = new Document( cu.getSource() );
                parser.setResolveBindings( true );
                CompilationUnit ast = (CompilationUnit)parser.createAST( null );
                ast.recordModifications();
                StackVisitor visitor = new StackVisitor( ast.getAST() );
                ast.accept( visitor );

                TextEdit edits = ast.rewrite( document , cu.getJavaProject().getOptions( true ) );
                edits.apply( document );
                String newSource = document.get();
                cu.getBuffer().setContents( newSource );
                cu.save( null , true );

            } catch ( JavaModelException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch ( MalformedTreeException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch ( BadLocationException e ) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Selection in the workbench has been changed. We can change the state of
     * the 'real' action here if we want, but this can only happen after the
     * delegate has been created.
     * @see IWorkbenchWindowActionDelegate#selectionChanged
     */
    public void selectionChanged( IAction action , ISelection selection ) {
        if ( selection instanceof IStructuredSelection ) {
            IStructuredSelection structured = (IStructuredSelection)selection;
            this.classes = structured.toList();
        } else {
            this.classes = new ArrayList<ICompilationUnit>();
        }
    }

    /**
     * We can use this method to dispose of any system resources we previously
     * allocated.
     * @see IWorkbenchWindowActionDelegate#dispose
     */
    public void dispose() {
    }

    /**
     * We will cache window object in order to be able to provide parent shell
     * for the message dialog.
     * @see IWorkbenchWindowActionDelegate#init
     */
    public void init( IWorkbenchWindow window ) {
        this.window = window;
    }

    public ICompilationUnit getCompilationUnit() {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject project = root.getProject( "SimpleStack" );
        IFile file = project.getFile( "src/Stack.java" );
        return JavaCore.createCompilationUnitFrom( file );
    }
}