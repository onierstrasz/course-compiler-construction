//
// Generated by JTB 1.3.2
//

package syntaxtree;

/**
 * Grammar production:
 * f0 -> "("
 * f1 -> StmList()
 * f2 -> ","
 * f3 -> Exp()
 * f4 -> ")"
 */
public class StmExp implements Node {
   public NodeToken f0;
   public StmList f1;
   public NodeToken f2;
   public Exp f3;
   public NodeToken f4;

   public StmExp(NodeToken n0, StmList n1, NodeToken n2, Exp n3, NodeToken n4) {
      f0 = n0;
      f1 = n1;
      f2 = n2;
      f3 = n3;
      f4 = n4;
   }

   public StmExp(StmList n0, Exp n1) {
      f0 = new NodeToken("(");
      f1 = n0;
      f2 = new NodeToken(",");
      f3 = n1;
      f4 = new NodeToken(")");
   }

   public void accept(visitor.Visitor v) {
      v.visit(this);
   }
   public <R,A> R accept(visitor.GJVisitor<R,A> v, A argu) {
      return v.visit(this,argu);
   }
   public <R> R accept(visitor.GJNoArguVisitor<R> v) {
      return v.visit(this);
   }
   public <A> void accept(visitor.GJVoidVisitor<A> v, A argu) {
      v.visit(this,argu);
   }
}

