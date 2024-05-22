/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package performance_measure_groupwork;

import java.util.LinkedList;
/**
 *
 * @author Mbete
 */
public class Performance_measure_groupwork {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Read input data (replace with your data reading logic)
        Customer[] customers = {
                new Customer(1, 1.9, 1.7),
                new Customer(2, 1.3, 3.6),
                new Customer(3, 1.1, 5.4),
                new Customer(4, 1.0, 6.9),
                new Customer(5, 2.2, 7.8),
                new Customer(6, 2.1, 8.4),
                new Customer(7, 1.8, 10.1),
                new Customer(8, 2.8, 12.5),
                new Customer(9, 2.7, 16.0),
                new Customer(10, 2.4, 17.7)
               
        };

        // Simulate the queue
        int systemTime = 0;
        LinkedList<Customer> queue = new LinkedList<>();
        int serverIdleTime = 0;
        int maxQueueLength = 0;
        int totalWaitingTime = 0;
        int totalSystemTime = 0;
        int totalCustomers = customers.length;

        System.out.println("Customer | IAT | Clock Time | Service Time | Service Starts | Service Ends | Customers in System | Customers in Queue | Waiting Time | Time in System | Idle Time of Server");

        for (Customer customer : customers) {
            systemTime += customer.getIAT();
            customer.setClockTime(systemTime);
            queue.addLast(customer);

            if (queue.size() > maxQueueLength) {
                maxQueueLength = queue.size();
            }

            if (serverIdleTime > 0) {
                serverIdleTime -= customer.getServiceTime();
            }

            if (serverIdleTime <= 0) {
                Customer departingCustomer = queue.removeFirst();
                departingCustomer.setServiceStart(systemTime);
                departingCustomer.setServiceEnd((int) (systemTime + departingCustomer.getServiceTime()));
                departingCustomer.setWaitingTime(departingCustomer.getServiceStart() - departingCustomer.getClockTime());
                departingCustomer.setTimeInSystem((int) (departingCustomer.getWaitingTime() + departingCustomer.getServiceTime()));
                serverIdleTime = 0;
                totalWaitingTime += departingCustomer.getWaitingTime();
                totalSystemTime += departingCustomer.getTimeInSystem();
            }

            // Print customer details
            System.out.println(customer);
        }

        // Calculate queue statistics
        double avgWaitingTime = (double) totalWaitingTime / totalCustomers;
        double avgSystemTime = (double) totalSystemTime / totalCustomers;

        // Print queue statistics
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.println("Average Time in System: " + avgSystemTime);
        System.out.println("Maximum Queue Length: " + maxQueueLength);
        // Calculate server idle time (subtract the total service time from the total simulation time)
        int totalServiceTime = 0;
        for (Customer customer : customers) {
            totalServiceTime += customer.getServiceTime();
        }
        int totalSimulationTime = (int) (customers[customers.length - 1].getClockTime() + customers[customers.length - 1].getServiceTime());
        int serverIdleTimeFinal = totalSimulationTime - totalServiceTime;
        System.out.println("Server Idle Time: " + serverIdleTimeFinal);
    }
}

class Customer {
    private int customerNumber;
    private double IAT;
    private double serviceTime;
    private int clockTime;
    private int serviceStart;
    private int serviceEnd;
    private int waitingTime;
    private int timeInSystem;

    public Customer(int customerNumber, double IAT, double serviceTime) {
        this.customerNumber = customerNumber;
        this.IAT = IAT;
        this.serviceTime = serviceTime;
    }

    // Getters and setters
    public int getCustomerNumber() {
        return customerNumber;
    }

    public double getIAT() {
        return IAT;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public int getClockTime() {
        return clockTime;
    }

    public void setClockTime(int clockTime) {
        this.clockTime = clockTime;
    }

    public int getServiceStart() {
        return serviceStart;
    }

    public void setServiceStart(int serviceStart) {
        this.serviceStart = serviceStart;
    }

    public int getServiceEnd() {
        return serviceEnd;
    }

    public void setServiceEnd(int serviceEnd) {
        this.serviceEnd = serviceEnd;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTimeInSystem() {
        return timeInSystem;
    }

    public void setTimeInSystem(int timeInSystem) {
        this.timeInSystem = timeInSystem;
    }

    @Override
    public String toString() {
        return customerNumber + " | " + IAT + " | " + clockTime + " | " + serviceTime + " | " + serviceStart + " | " + serviceEnd + " | " + clockTime + " | " + (clockTime - serviceStart) + " | " + waitingTime + " | " + timeInSystem + " | 0";
    }
    
}
