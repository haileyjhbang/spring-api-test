package com.boot.api.customer;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.boot.api.customer.Customer.CustomerStatus;

public class CustomerService {

  public static void main(String[] args) {
    System.out.println("Hello world");
    System.out.println("Current working directory: " + System.getProperty("user.dir"));
    // 아래에 문제 해결을 위한 코드를 작성해주세요.
    CustomerService service = new CustomerService();
    service.generateDormantFile();
    service.generateTotalFile();
  }


    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);


    public void generateTotalFile(){
      // String json = FileUtils.objectToJson(getTotal());
        // FileUtils.toFile("/problem_1.json", json);
        FileUtils.toFile("/problem_1.json", getTotal());
    }

    private Total getTotal(){
        return new Total(getCustomers().size());
    }

    public void generateDormantFile(){
        FileUtils.toFile("/problem_2.json", getDormants());
    }

    private List<Long> getDormants(){
        List<Customer> customers = getCustomers();
        return customers.stream().filter(customer -> customer.getStatus().equals(CustomerStatus.dormant)).map(customer -> Long.parseLong(customer.getCustomerId())).sorted().collect(Collectors.toUnmodifiableList());
    }

    private List<Customer> getCustomers(){
        return FileUtils.fileToList("/customer.json", Customer.class);
    }
}
