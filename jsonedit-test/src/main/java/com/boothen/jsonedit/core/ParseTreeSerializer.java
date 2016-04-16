package com.boothen.jsonedit.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.jface.text.Position;

/**
 * Converts a {@link ParseTree} to a list of text strings.
 */
public class ParseTreeSerializer {

    public static void comp(ParseTree syntaxTree, Map<ParseTree, Position> positions,
            ParseTree oldRoot, Map<ParseTree, Position> oldPositions) {
        List<String> newTreeText = ParseTreeSerializer.toString(syntaxTree, positions);
        List<String> oldTreeText = ParseTreeSerializer.toString(oldRoot, oldPositions);
        for (int i = 0; i < Math.max(newTreeText.size(), oldTreeText.size()); i++) {
            String left = (i < newTreeText.size()) ? newTreeText.get(i) : "";
            String right = (i < oldTreeText.size()) ? oldTreeText.get(i) : "";
            String both = String.format("%-60.60s | %-60.60s", left, right);
            System.out.println(both);
        }
    }

    public static List<String> toString(ParseTree syntaxTree, Map<ParseTree, Position> positions) {
        List<String> list = new ArrayList<>();
        toString(list, syntaxTree, positions, 0);
        return list;
    }

    private static void toString(List<String> list, ParseTree tree, Map<ParseTree, Position> positions, int level) {

        String name = tree.getClass().getSimpleName();
        if (tree instanceof TerminalNode) {
            name = "'" + ((TerminalNode)tree).getText() + "'";
        }
        Position pos = positions.get(tree);

        String text = "";
        for (int i = 0; i < level; i++) {
            text += "  ";
        }
        text += String.format("(%s) -> %s", pos, name);
        list.add(text);

        for (int i = 0; i < tree.getChildCount(); i++) {
            ParseTree child= tree.getChild(i);
            toString(list, child, positions, level + 1);
        }
    }

}
