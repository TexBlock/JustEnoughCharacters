package towdium.je_characters.jei;

import gnu.trove.set.TIntSet;
import mezz.jei.suffixtree.GeneralizedSuffixTree;
import org.objectweb.asm.tree.ClassNode;
import towdium.je_characters.JechConfig;
import towdium.je_characters.core.JechCore;
import towdium.je_characters.transform.Transformer;

/**
 * Author: Towdium
 * Date:   13/06/17
 */
public class TransformerJei implements Transformer.Extended {
    public static void loadingHook() {
    }

    @Override
    public boolean accepts(String name) {
        return JechConfig.EnumItems.EnableJEI.getProperty().getBoolean() && (
                name.equals("mezz.jei.ItemFilterInternals")
                        || name.equals("mezz.jei.ingredients.IngredientFilterInternals")
                        || name.equals("mezz.jei.ingredients.IngredientFilter")
        );
    }

    @Override
    public void transform(ClassNode n) {
        JechCore.LOG.info("Transforming class " + n.name + " for JEI integration.");
        Transformer.findMethod(n, "<init>").ifPresent(methodNode -> {
            Transformer.transformConstruct(methodNode, "mezz/jei/suffixtree/GeneralizedSuffixTree",
                    "towdium/je_characters/jei/TransformerJei$FakeTreeB");
            Transformer.transformConstruct(methodNode, "com/abahgat/suffixtree/GeneralizedSuffixTree",
                    "towdium/je_characters/jei/TransformerJei$FakeTreeA");
            Transformer.transformHook(methodNode, "towdium/je_characters/jei/TransformerJei",
                    "loadingHook", "()V");
        });
        Transformer.findMethod(n, "createPrefixedSearchTree").ifPresent(methodNode ->
                Transformer.transformConstruct(methodNode, "mezz/jei/suffixtree/GeneralizedSuffixTree",
                        "towdium/je_characters/jei/TransformerJei$FakeTreeB"));
        Transformer.findMethod(n, "buildSuffixTrees").ifPresent(methodNode ->
                Transformer.transformHook(methodNode, "towdium/je_characters/jei/TransformerJei",
                        "loadingHook", "()V"));
    }

    public static class FakeTreeB extends GeneralizedSuffixTree {
        CachedFilter cf;

        public FakeTreeB() {
            cf = new CachedFilter();
        }

        public TIntSet search(String word) {
            return cf.search(word);
        }

        public void put(String key, int index) throws IllegalStateException {
            cf.put(key, index);
        }

        public int getHighestIndex() {
            return cf.computeCount();
        }
    }

    public static class FakeTreeA extends com.abahgat.suffixtree.GeneralizedSuffixTree {
        CachedFilter cf;

        public FakeTreeA() {
            cf = new CachedFilter();
        }

        public TIntSet search(String word) {
            return cf.search(word);
        }

        public void put(String key, int index) throws IllegalStateException {
            cf.put(key, index);
        }

        public int computeCount() {
            return cf.computeCount();
        }
    }
}
