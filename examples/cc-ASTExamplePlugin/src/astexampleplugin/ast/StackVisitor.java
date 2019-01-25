
package astexampleplugin.ast;

import java.util.HashMap;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;


public class StackVisitor
        extends ASTVisitor {

    private static final String PREFIX = "_";
    private final AST           ast;
    private String newTypeName;
    private HashMap<String, String> fields;


    public StackVisitor( AST ast ) {
        this.ast = ast;
        this.fields = new HashMap<String, String>();
    }

    public boolean visit(FieldDeclaration field){
        VariableDeclarationFragment fragment = (VariableDeclarationFragment)field.fragments().get( 0 );
        String oldName = fragment.getName().toString();
        String newName = this.fields.get( oldName );
        if(newName == null){            
            newName = PREFIX + oldName;
            this.fields.put( oldName , newName );
        }
        fragment.setName( this.ast.newSimpleName( newName ) );
        return true;
    }
    
    public boolean visit(FieldAccess fieldAccess){
        String oldName = fieldAccess.getName().toString();
        String newName = this.fields.get( oldName );
        if(newName == null){
            newName = PREFIX + oldName;
            this.fields.put( oldName , newName );
        }
        fieldAccess.setName( this.ast.newSimpleName( newName ) );
        return true;
    }

    public String getTypeName() {
        return this.newTypeName;
    }

}
