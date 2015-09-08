/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.progtech.bead.gaf6rk.auction.gui;

import hu.elte.progtech.bead.gaf6rk.auction.db.dao.DaoManager;
import hu.elte.progtech.bead.gaf6rk.auction.db.entity.*;
import static hu.elte.progtech.bead.gaf6rk.auction.gui.FrameConstants.*;
import hu.elte.progtech.bead.gaf6rk.auction.gui.helper.*;
import hu.elte.progtech.bead.gaf6rk.auction.gui.model.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author gaf6rk
 */
public class AuctionFrame extends JFrame {

    private JTable table;
    private CombinationPanel combinationPanel;
    private JToolBar toolBar;
    private JButton addGoodButton;
    private JButton removeGoodButton;
    private JButton addBidButton;
    
    private ListSelectionListener selectionListener;
    private ActionListener addGoodAction;
    private ActionListener addBidToGoodAction;
    private ActionListener removeGoodAction;
    
    public AuctionFrame() {
        linkActionListeners();
        
        initFrame();
        initTable();
        initButtons();
        initToolBar();
        initCombinationPanel();
        
        fill();
    }

    private void initFrame() {
        setTitle(FrameConstants.FRAME_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
    }
    
    private void initTable() {
        GenericTableModel<Good> model = new GenericTableModel<>(DaoManager.getInstance().getGoodDao(), Good.PROPERTY_NAMES, Good.CLASS_TYPES);
        TableRowSorter sorter = new TableRowSorter<>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(selectionListener);
    }
    
    private void initButtons() {
        addGoodButton = new JButton(GOOD_ADD_BUTTON_TEXT);
        addGoodButton.addActionListener(addGoodAction);
        removeGoodButton = new JButton(GOOD_REMOVE_BUTTON_TEXT);
        removeGoodButton.addActionListener(removeGoodAction);
        removeGoodButton.setEnabled(false);
        
        addBidButton = new JButton(GOOD_ADD_BID_BUTTON_TEXT);
        addBidButton.addActionListener(addBidToGoodAction);
        addBidButton.setEnabled(false);
    }
    
    private void initToolBar() {
        toolBar = new JToolBar();
        toolBar.add(addGoodButton);
        toolBar.add(addBidButton);
        toolBar.add(removeGoodButton);
    }
    
    private void initCombinationPanel() {
        combinationPanel = new CombinationPanel(this);
    }
    
    private void fill() {
        add(toolBar, BorderLayout.PAGE_START);
        add(combinationPanel, BorderLayout.LINE_END);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void linkActionListeners() {
        
        selectionListener = new ListSelectionListener() {
            
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (table.getSelectedRow() > -1) {
                    GenericTableModel<Good> model = (GenericTableModel<Good>) table.getModel();
                    int rowIndex = table.convertRowIndexToModel(table.getSelectedRow());
                    Good selected = model.getEntity(rowIndex);
                    if (selected != null) {
                        combinationPanel.setGood(selected);
                        removeGoodButton.setEnabled(true);
                        addBidButton.setEnabled(true);
                    }
                } else {
                    removeGoodButton.setEnabled(false);
                    addBidButton.setEnabled(false);
                }
            }
        };
        
        addGoodAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Good good = GoodCreator.preparation()
                        .withName()
                        .withCategory()
                        .withCloseDate()
                        .withOriginalBid()
                        .build();
                
                if (good == null) return;
                
                GenericTableModel<Good> model = (GenericTableModel<Good>) table.getModel();
                model.addEntity(good);
            }
        };
        
        addBidToGoodAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Bid bid = BidCreator.preparation()
                        .withBidder()
                        .withBidQ()
                        .call();
                
                combinationPanel.addBid(bid);
                table.repaint();
            }
        };
        
        removeGoodAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (table.getSelectedRow() > -1) {
                    GenericTableModel<Good> model = (GenericTableModel<Good>) table.getModel();
                    int rowIndex = table.convertRowIndexToModel(table.getSelectedRow());
                    model.removeEntity(rowIndex);
                    combinationPanel.setGood(null);
                }
            }
        };
    }
    
    
    
}
