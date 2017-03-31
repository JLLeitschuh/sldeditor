/*
 * SLD Editor - The Open Source Java SLD Editor
 *
 * Copyright (C) 2016, SCISYS UK Limited
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.sldeditor.ui.detail.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.opengis.filter.expression.Expression;

import com.sldeditor.common.undo.UndoActionInterface;
import com.sldeditor.common.undo.UndoEvent;
import com.sldeditor.common.undo.UndoInterface;
import com.sldeditor.common.undo.UndoManager;
import com.sldeditor.common.xml.ui.FieldIdEnum;
import com.sldeditor.ui.detail.BasePanel;
import com.sldeditor.ui.widgets.FieldPanel;

/**
 * The Class FieldConfigString wraps a text field GUI component and an
 * optional value/attribute/expression drop down,
 * 
 * <p>Supports undo/redo functionality.
 * 
 * <p>Instantiated by {@link com.sldeditor.ui.detail.config.ReadPanelConfig}
 * 
 * @author Robert Ward (SCISYS)
 */
public class FieldConfigString extends FieldConfigBase implements UndoActionInterface {

    /** The text field. */
    private JTextField textField;

    /** The default value. */
    private String defaultValue = "";

    /** The old value obj. */
    private Object oldValueObj = null;

    /** The button text. */
    private String buttonText = null;

    /** The button pressed listener list. */
    private List<FieldConfigStringButtonInterface> buttonPressedListenerList = null;

    /** The suppress update on set flag. */
    private boolean suppressUpdateOnSet = false;

    /**
     * Instantiates a new field config string.
     *
     * @param commonData the common data
     * @param buttonText the button text
     */
    public FieldConfigString(FieldConfigCommonData commonData, String buttonText) {
        super(commonData);

        this.buttonText = buttonText;
    }

    /**
     * Creates the ui.
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.sldeditor.ui.detail.config.FieldConfigBase#createUI()
     */
    @Override
    public void createUI() {
        if (textField == null) {
            final UndoActionInterface parentObj = this;

            int xPos = getXPos();
            FieldPanel fieldPanel = createFieldPanel(xPos, getLabel());

            textField = new TextFieldPropertyChange();
            textField.setBounds(
                    xPos + BasePanel.WIDGET_X_START, 0, this.isValueOnly()
                            ? BasePanel.WIDGET_EXTENDED_WIDTH : BasePanel.WIDGET_STANDARD_WIDTH,
                    BasePanel.WIDGET_HEIGHT);
            fieldPanel.add(textField);

            textField.addPropertyChangeListener(TextFieldPropertyChange.TEXT_PROPERTY,
                    new PropertyChangeListener() {

                        /*
                         * (non-Javadoc)
                         * 
                         * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
                         */
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            String originalValue = (String) evt.getOldValue();
                            String newValueObj = (String) evt.getNewValue();

                            if ((originalValue.compareTo(newValueObj) != 0)) {
                                if (!suppressUpdateOnSet) {
                                    UndoManager.getInstance().addUndoEvent(new UndoEvent(parentObj,
                                            getFieldId(), oldValueObj, newValueObj));

                                    oldValueObj = originalValue;
                                }

                                valueUpdated();
                            }
                        }
                    });

            if (buttonText != null) {
                final JButton buttonExternal = new JButton(buttonText);
                buttonExternal.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        if (buttonPressedListenerList != null) {
                            //CHECKSTYLE:OFF
                            for (FieldConfigStringButtonInterface listener : buttonPressedListenerList) {
                                listener.buttonPressed(buttonExternal);
                            }
                            //CHECKSTYLE:ON
                        }
                    }
                });

                int buttonWidth = 26;
                int padding = 3;
                buttonExternal.setBounds(xPos + textField.getX() - buttonWidth - padding, 0,
                        buttonWidth, BasePanel.WIDGET_HEIGHT);
                fieldPanel.add(buttonExternal);
            }

            if (!isValueOnly()) {
                setAttributeSelectionPanel(
                        fieldPanel.internalCreateAttrButton(String.class, this, isRasterSymbol()));
            }
        }
    }

    /**
     * Attribute selection.
     *
     * @param field the field
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.sldeditor.ui.iface.AttributeButtonSelectionInterface#attributeSelection(java.lang.String)
     */
    @Override
    public void attributeSelection(String field) {
        // Not used
    }

    /**
     * Sets the enabled.
     *
     * @param enabled the new enabled
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.sldeditor.ui.detail.config.FieldConfigBase#setEnabled(boolean)
     */
    @Override
    public void internal_setEnabled(boolean enabled) {
        if (textField != null) {
            textField.setEnabled(enabled);
        }
    }

    /**
     * Generate expression.
     *
     * @return the expression
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.sldeditor.ui.detail.config.FieldConfigBase#generateExpression()
     */
    @Override
    protected Expression generateExpression() {
        Expression expression = null;

        if (this.textField != null) {
            expression = getFilterFactory().literal(textField.getText());
        }
        return expression;
    }

    /**
     * Checks if is enabled.
     *
     * @return true, if is enabled
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.sldeditor.ui.detail.config.FieldConfigBase#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        if ((attributeSelectionPanel != null) && !isValueOnly()) {
            return attributeSelectionPanel.isEnabled();
        } else {
            if (textField != null) {
                return textField.isEnabled();
            }
        }
        return false;
    }

    /**
     * Revert to default value.
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.sldeditor.ui.detail.config.FieldConfigBase#revertToDefaultValue()
     */
    @Override
    public void revertToDefaultValue() {
        populateField(defaultValue);
    }

    /**
     * Populate expression.
     *
     * @param objValue the obj value
     */
    /*
     * (non-Javadoc)
     * 
     * @see com.sldeditor.ui.detail.config.FieldConfigBase#populateExpression(java.lang.Object)
     */
    @Override
    public void populateExpression(Object objValue) {
        if (objValue instanceof String) {
            String sValue = (String) objValue;

            populateField(sValue);
        }
    }

    /**
     * Sets the default value.
     *
     * @param defaultValue the new default value
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Gets the string value.
     *
     * @return the string value
     */
    @Override
    public String getStringValue() {
        if (textField != null) {
            if (getPanel().isValueReadable()) {
                return textField.getText();
            }
        }
        return null;
    }

    /**
     * Undo action.
     *
     * @param undoRedoObject the undo/redo object
     */
    @Override
    public void undoAction(UndoInterface undoRedoObject) {
        if (textField != null) {
            if (undoRedoObject != null) {
                String oldValue = (String) undoRedoObject.getOldValue();

                textField.setText(oldValue);
            }
        }
    }

    /**
     * Redo action.
     *
     * @param undoRedoObject the undo/redo object
     */
    @Override
    public void redoAction(UndoInterface undoRedoObject) {
        if (textField != null) {
            if (undoRedoObject != null) {
                String newValue = (String) undoRedoObject.getNewValue();

                textField.setText(newValue);
            }
        }
    }

    /**
     * Adds the button pressed listener.
     *
     * @param listener the listener
     */
    public void addButtonPressedListener(FieldConfigStringButtonInterface listener) {

        if (buttonPressedListenerList == null) {
            buttonPressedListenerList = new ArrayList<FieldConfigStringButtonInterface>();
        }

        buttonPressedListenerList.add(listener);
    }

    /**
     * Sets the test string value.
     *
     * @param fieldId the field id
     * @param testValue the test value
     */
    @Override
    public void setTestValue(FieldIdEnum fieldId, String testValue) {
        populateField(testValue);
    }

    /**
     * Populate field.
     *
     * @param value the value
     */
    @Override
    public void populateField(String value) {
        if (textField != null) {
            textField.setText(value);

            if (!suppressUpdateOnSet) {
                UndoManager.getInstance()
                        .addUndoEvent(new UndoEvent(this, getFieldId(), oldValueObj, value));

                oldValueObj = value;

                valueUpdated();
            }
        }
    }

    /**
     * Creates a copy of the field.
     *
     * @param fieldConfigBase the field config base
     * @return the field config base
     */
    @Override
    protected FieldConfigBase createCopy(FieldConfigBase fieldConfigBase) {
        FieldConfigString copy = null;

        if (fieldConfigBase != null) {
            copy = new FieldConfigString(fieldConfigBase.getCommonData(), this.buttonText);
        }
        return copy;
    }

    /**
     * Sets the field visible.
     *
     * @param visible the new visible state
     */
    @Override
    public void setVisible(boolean visible) {
        if (textField != null) {
            textField.setVisible(visible);
        }
    }

    /**
     * Sets the suppress updates on set.
     *
     * @param suppressUpdateOnSet the new suppress updates on set
     */
    public void setSuppressUpdatesOnSet(boolean suppressUpdateOnSet) {
        this.suppressUpdateOnSet = suppressUpdateOnSet;
    }
}
