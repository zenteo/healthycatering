package edu.hist.team3.catering.gui.tab;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

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
		jobSearch = new JobSearch(services);

		centerPanel.add(employeeSearch);
		centerPanel.add(jobSearch);

		JButton addEmployeeButton = new JButton("Add employee");
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
		
		JButton addEmployeeButton2 = new JButton("Employ customer");
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

		JButton editEmployeeButton = new JButton("Edit employee");
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

		JButton getEmployeeButton = new JButton("Get info");
		getEmployeeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Employee selected = employeeSearch.getSelected();
				if (selected != null) {
					Job job = selected.getJob();
					double wage = job.getHourlySalary() * selected.getSessionHours();
					Calendar now = Calendar.getInstance();
					Calendar employment = Calendar.getInstance();
					employment.setTime(selected.getEmploymentDate());
					now.add(Calendar.YEAR, -employment.get(Calendar.YEAR));
					now.add(Calendar.DAY_OF_YEAR, -employment.get(Calendar.DAY_OF_YEAR));
					System.out.println(now.get(Calendar.DAY_OF_YEAR));
					double years = now.get(Calendar.YEAR);
					years += now.get(Calendar.DAY_OF_YEAR) / 365.25;
					years += now.get(Calendar.HOUR_OF_DAY) / 24 / 365.25;
					years += now.get(Calendar.MINUTE) / 60.0 / 24 / 365.25;
					years += now.get(Calendar.SECOND) / 60.0 / 60.0 / 24 / 365.25;;
					double yearlyWage = wage / years;
					String message = "Used this program for: " + selected.getSessionHours() + " hours!\n";
					message += "Wage til now, based on this number: " + wage + "\n";
					message += "Yearly wage ,based on this number: " + yearlyWage + "\n";
					Services.showMessage(message);
				}
				else {
					Services.showError("Select an employee first!");
				}
			}

		});

		JButton removeEmployeeButton = new JButton("Remove employee");
		removeEmployeeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (employeeSearch.getSelected() != null) {
					if (Services.choiceMessage("Do you really want to remove the employee?", "Are you sure?")) {
						try {
							employeeSearch.getSelected().remove();
							employeeSearch.doSearch();
						}
						catch (SQLException e1) {
							Services.showError("Error: Could not remove employee!");
							e1.printStackTrace();
						}						
					}
				}
				else {
					Services.showError("Select an employee first!");
				}
			}

		});

		JButton addJobButton = new JButton("Add job");
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

		JButton editJobButton = new JButton("Edit job");
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

		JButton removeJobButton = new JButton("Remove job");
		removeJobButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jobSearch.getSelected() != null) {
					if (Services.choiceMessage("Do you really want to remove the job?", "Are you sure?")) {
						try {
							jobSearch.getSelected().remove();
							jobSearch.doSearch();
						} catch (SQLException e1) {
							Services.showError("Error: Could not remove job.");
							e1.printStackTrace();
						}
					}
				} else {
					Services.showError("Select a job first!");
				}
			}
		});

		JButton giveJobButton = new JButton("Give job");
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
		rightButtonPanel.setLayout(new GridLayout(4, 1));
		rightButtonPanel.add(addJobButton);
		rightButtonPanel.add(editJobButton);
		rightButtonPanel.add(removeJobButton);
		rightButtonPanel.add(giveJobButton);
		
		JPanel leftButtonPanel = new JPanel();
		leftButtonPanel.setLayout(new GridLayout(5, 1));
		leftButtonPanel.add(addEmployeeButton);
		leftButtonPanel.add(addEmployeeButton2);
		leftButtonPanel.add(editEmployeeButton);
		leftButtonPanel.add(getEmployeeButton);
		leftButtonPanel.add(removeEmployeeButton);

		JPanel rightHolder = new JPanel();
		rightHolder.setLayout(new BorderLayout());
		rightHolder.add(rightButtonPanel, BorderLayout.NORTH);
		
		JPanel leftHolder = new JPanel();
		leftHolder.setLayout(new BorderLayout());
		leftHolder.add(leftButtonPanel, BorderLayout.NORTH);
		
		add(rightHolder, BorderLayout.EAST);
		add(centerPanel, BorderLayout.CENTER);
		add(leftHolder, BorderLayout.WEST);
	}
}
