package edu.hist.team3.catering.gui.tab;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.hist.team3.catering.database.Customer;
import edu.hist.team3.catering.database.Employee;
import edu.hist.team3.catering.database.Job;
import edu.hist.team3.catering.database.manager.Services;
import edu.hist.team3.catering.gui.frame.CustomerSearchFrame;
import edu.hist.team3.catering.gui.frame.EmployeeEditFrame;
import edu.hist.team3.catering.gui.frame.JobEditFrame;
import edu.hist.team3.catering.gui.panel.EmployeeSearch;
import edu.hist.team3.catering.gui.panel.JobSearch;

/*
 * Boss GUI
 --
 --
 + addEmployee()
 + editEmployee()
 + getEmployeeStats()
 + removeEmployee()
 + addJob()
 + editJob()
 + removeJob()
 */
@SuppressWarnings("serial")
public class BossTab extends JPanel {
	private Services services;
	private EmployeeSearch employeeSearch;
	private JobSearch jobSearch;
	private JList<Job> jobList;

	public BossTab(final Services services) {
		setLayout(new BorderLayout());
		this.services = services;
		Dimension buttonDimension = new Dimension(150, 70);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1, 2));
		employeeSearch = new EmployeeSearch(services);
		employeeSearch.getResultList().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent arg0) {

					}
				});

		jobSearch = new JobSearch(services);
		jobSearch.getResultList().addListSelectionListener(
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent arg0) {

					}
				});

		centerPanel.add(employeeSearch);
		centerPanel.add(jobSearch);
		add(centerPanel, BorderLayout.CENTER);

		JButton addEmployeeButton = new JButton("Add Employee");
		addEmployeeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jobSearch.getSelected() != null) {
					try {
						Employee employee = services.getEmployeeManager().addEmployee(jobSearch.getSelected());
						EmployeeEditFrame editFrame = new EmployeeEditFrame(employee);
						editFrame.addComponentListener(new ComponentAdapter() {
							@Override
							public void componentHidden(ComponentEvent arg0) {
								employeeSearch.doSearch();
							}
						});
						editFrame.setVisible(true);
					}
					catch (SQLException e1) {
						Services.showError("Error: Could not create employee!");
						e1.printStackTrace();
					}
				}
				else {
					Services.showError("Select a job first!");
				}
			}

		});
		
		JButton addEmployeeButton2 = new JButton("Upgrade customer");
		addEmployeeButton2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final Job job = jobSearch.getSelected();
				if (job != null) {
					final CustomerSearchFrame customerSearch = new CustomerSearchFrame(services);
					customerSearch.addComponentListener(new ComponentAdapter() {
						@Override
						public void componentHidden(ComponentEvent arg0) {
							Customer customer = customerSearch.getSelected();
							if (customer != null) {
								try {
									Employee employee = services.getEmployeeManager().addEmployee(customer, job);
									EmployeeEditFrame editFrame = new EmployeeEditFrame(employee);
									editFrame.addComponentListener(new ComponentAdapter() {
										@Override
										public void componentHidden(ComponentEvent arg0) {
											employeeSearch.doSearch();
										}
									});
									editFrame.setVisible(true);
								} catch (SQLException e) {
									Services.showError("Error: Could not create employee!");
									e.printStackTrace();
								}
							}
						}
					});
					customerSearch.setVisible(true);
				}
				else {
					Services.showError("Select a job first!");
				}
			}

		});

		JButton editEmployeeButton = new JButton("Edit Employee");
		editEmployeeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (employeeSearch.getSelected() != null) {
					EmployeeEditFrame editFrame = new EmployeeEditFrame(employeeSearch.getSelected());
					editFrame.addComponentListener(new ComponentAdapter() {
						@Override
						public void componentHidden(ComponentEvent arg0) {
							employeeSearch.doSearch();
						}
					});
					editFrame.setVisible(true);
				}
				else {
					Services.showError("Select an employee first!");
				}
			}

		});

		JButton getEmployeeButton = new JButton("Get Employee Stats");
		getEmployeeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (employeeSearch.getSelected() != null) {
					Services.showMessage("Used this program for: " + employeeSearch.getSelected().getSessionHours() + " hours!");
				}
				else {
					Services.showError("Select an employee first!");
				}
			}

		});

		JButton removeEmployeeButton = new JButton("Remove Employee");
		removeEmployeeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (employeeSearch.getSelected() != null) {
					try {
						employeeSearch.getSelected().remove();
					}
					catch (SQLException e1) {
						Services.showError("Error: Could not remove employee!");
						e1.printStackTrace();
					}
				}
				else {
					Services.showError("Select an employee first!");
				}
			}

		});

		JButton addJobButton = new JButton("Add Job");
		addJobButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JobEditFrame jobEditor = new JobEditFrame(services);
				jobEditor.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentHidden(ComponentEvent arg0) {
						jobSearch.doSearch();
					}
				});
				jobEditor.setVisible(true);
			}

		});

		JButton editJobButton = new JButton("Edit Job");
		editJobButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jobSearch.getSelected() != null) {
					JobEditFrame jobEditor = new JobEditFrame(services,
							jobSearch.getSelected());
					jobEditor.addComponentListener(new ComponentAdapter() {
						@Override
						public void componentHidden(ComponentEvent arg0) {
							jobSearch.doSearch();
						}
					});
					jobEditor.setVisible(true);
				} else {
					Services.showError("Select a job first!");
				}
			}

		});

		JButton removeJobButton = new JButton("Remove Job");
		removeJobButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jobSearch.getSelected() != null) {
					try {
						jobSearch.getSelected().remove();
						jobSearch.doSearch();
					} catch (SQLException e1) {
						Services.showError("Error: Could not remove job.");
						e1.printStackTrace();
					}
				} else {
					Services.showError("Select a job first!");
				}
			}
		});

		JButton giveJobButton = new JButton("Give Job");
		giveJobButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Employee employee = employeeSearch.getSelected();
				if (employee != null) {
					if (jobSearch.getSelected() != null) {
						try {
							employee.setJob(jobSearch.getSelected());
							employee.commit();
						} catch (SQLException e1) {
							Services.showError("Error: Could give job.");
							e1.printStackTrace();
						}
					} else {
						Services.showError("Select a job first!");
					}
				} else {
					Services.showError("Select a employee first!");
				}
			}
		});

		JPanel rightButtonPanel = new JPanel();
		rightButtonPanel.setLayout(new BoxLayout(rightButtonPanel,
				BoxLayout.PAGE_AXIS));
		rightButtonPanel.add(addJobButton);
		rightButtonPanel.add(editJobButton);
		rightButtonPanel.add(removeJobButton);
		rightButtonPanel.add(giveJobButton);

		JPanel leftButtonPanel = new JPanel();
		leftButtonPanel.setLayout(new BoxLayout(leftButtonPanel,
				BoxLayout.PAGE_AXIS));
		leftButtonPanel.add(addEmployeeButton);
		leftButtonPanel.add(addEmployeeButton2);
		leftButtonPanel.add(editEmployeeButton);
		leftButtonPanel.add(getEmployeeButton);
		leftButtonPanel.add(removeEmployeeButton);

		add(rightButtonPanel, BorderLayout.EAST);
		add(leftButtonPanel, BorderLayout.WEST);
	}
}
