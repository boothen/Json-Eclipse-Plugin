package com.boothen.jsonedit.model;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import com.boothen.jsonedit.antlr.JSONBaseVisitor;
import com.boothen.jsonedit.antlr.JSONParser.ObjectContext;
import com.boothen.jsonedit.antlr.JSONParser.PairContext;

/**
 * Recurses into tree finding all duplicate keys.
 */
public class DuplicateKeyFinder extends JSONBaseVisitor<Void> {

    private DuplicateKeyListener listener;

    /**
     * @param listener a single listener
     */
    public DuplicateKeyFinder(DuplicateKeyListener listener) {
        this.listener = listener;
    }

    @Override
    public Void visitObject(ObjectContext ctx) {
        Map<String, Token> keys = new HashMap<>();
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree child = ctx.getChild(i);
            if (child instanceof PairContext) {
                PairContext pair = (PairContext) child;
                Token keyToken = pair.STRING().getSymbol();
                String key = keyToken.getText();
                Token existing = keys.put(key, keyToken);
                if (existing != null) {
                    listener.reportDuplicate(key, existing, keyToken);
                }
            }
        }
        return super.visitObject(ctx);
    }
}
