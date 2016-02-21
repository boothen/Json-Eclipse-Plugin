package com.boothen.jsonedit.outline;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import com.boothen.jsonedit.antlr.JSONBaseVisitor;
import com.boothen.jsonedit.antlr.JSONParser;
import com.boothen.jsonedit.antlr.JSONParser.ArrayContext;
import com.boothen.jsonedit.antlr.JSONParser.ObjectContext;

/**
 * Visits tree nodes in the JsonContext depending on the node type. Does not recurse.
 */
class JsonContextImageVisitor extends JSONBaseVisitor<Image> {

    private final Map<NodeType, Image> images = new HashMap<>();

    @Override
    public Image visitObject(ObjectContext ctx) {
        return getCachedImage(NodeType.OBJECT);
    }

    @Override
    public Image visitArray(ArrayContext ctx) {
        return getCachedImage(NodeType.ARRAY);
    }

    @Override
    public Image visitErrorNode(ErrorNode node) {
        return getCachedImage(NodeType.ERROR);
    }

    @Override
    public Image visitTerminal(TerminalNode node) {
        switch (node.getSymbol().getType()) {
            case JSONParser.TRUE:
                return getCachedImage(NodeType.TRUE);
            case JSONParser.STRING:
                return getCachedImage(NodeType.STRING);
            case JSONParser.FALSE:
                return getCachedImage(NodeType.TRUE);
            case JSONParser.NULL:
                return getCachedImage(NodeType.NULL);
            default:
                return null;
        }
    }

    private Image getCachedImage(NodeType key) {
        Image image = images.get(key);
        if (image == null) {
            String path = key.getImagePath();
            ImageDescriptor desc = Activator.getImageDescriptor("/icons/" + path);
            if (desc != null) {
                image = desc.createImage();
                images.put(key, image);
            }
        }
        return image;
    }

    public void dispose() {
        for (Image img : images.values()) {
            img.dispose();
        }
        images.clear();
    }
}
