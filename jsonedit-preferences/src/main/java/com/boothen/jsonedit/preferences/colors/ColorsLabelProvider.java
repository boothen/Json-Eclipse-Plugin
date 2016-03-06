package com.boothen.jsonedit.preferences.colors;

import org.eclipse.jface.viewers.LabelProvider;

import com.boothen.jsonedit.preferences.NodeType;

public class ColorsLabelProvider extends LabelProvider
{
    @Override
    public String getText(Object element)
    {
        final NodeType type = (NodeType) element;

        switch (type)
        {
            case NULL:
                return "Null";
            case ARRAY:
                return "Array brackets";
            case BOOLEAN:
                return "Booleans";
            case OBJECT:
                return "Brackets";
//            case DEFAULT:
//                return "Default";
//            case KEYS:
//                return "Keys";
//            case MATCHED_BRACKET:
//                return "Matched bracket";
            case NUMBER:
                return "Numbers";
            case STRING:
                return "Strings";
            default:
                return type.name();
        }

    }
}