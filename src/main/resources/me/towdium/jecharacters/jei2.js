function initializeCoreMod() {
    Java.type('net.minecraftforge.coremod.api.ASMAPI').loadFile('me/towdium/jecharacters/lib.js');
    return {
        'jecharacters-jei2': {
            'target': {
                'type': 'METHOD',
                'class': 'mezz.jei.ingredients.IngredientFilter',
                'methodName': 'createPrefixedSearchTree',
                'methodDesc': '(CLmezz/jei/ingredients/PrefixedSearchTree$IModeGetter' +
                    'Lmezz/jei/ingredients/PrefixedSearchTree$IStringsGetter;)V'
            },
            'transformer': constructor(
                'mezz/jei/suffixtree/GeneralizedSuffixTree',
                'me/towdium/jecharacters/JechSearcher')
        }
    }
}