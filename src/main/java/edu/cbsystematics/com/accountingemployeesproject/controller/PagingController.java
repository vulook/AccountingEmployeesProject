package edu.cbsystematics.com.accountingemployeesproject.controller;

import edu.cbsystematics.com.accountingemployeesproject.config.PaginationConstants;
import edu.cbsystematics.com.accountingemployeesproject.model.response.EmployeeResponse;
import edu.cbsystematics.com.accountingemployeesproject.service.PagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/pageable")
public class PagingController {

    private static final Logger logger = LoggerFactory.getLogger(PagingController.class);

    private final PagingService pagingService;

    @Autowired
    public PagingController(PagingService pagingService) {
        this.pagingService = pagingService;
    }

    @GetMapping("/all")
    public ResponseEntity<EmployeeResponse> fetchAllEmployees(
            @RequestParam(value = "pageNumber", required = false, defaultValue = PaginationConstants.DEFAULT_PAGE + "") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = PaginationConstants.DEFAULT_PAGE_SIZE + "") int pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = PaginationConstants.DEFAULT_SORT) String sortField,
            @RequestParam(value = "sortDirection", required = false, defaultValue = PaginationConstants.DEFAULT_SORT_DIRECTION) String sortDirection) {

        System.out.println("PageNumber: " + pageNumber);
        System.out.println("PageSize: " + pageSize);
        System.out.println("SortField: " + sortField);
        System.out.println("SortDirection: " + sortDirection);

        try {
            // Build PageRequest using provided parameters
            PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.fromString(sortDirection), sortField);
            EmployeeResponse response = pagingService.findAllEmployees(pageRequest);

            // Log successful request at DEBUG level
            logger.debug("Fetched employees successfully. PageNumber: {}, PageSize: {}, SortDirection: {}, SortField: {}",
                    pageNumber, pageSize, sortDirection, sortField);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            // Log bad request and return a bad request response if input parameters are invalid
            logger.warn("Bad request for fetching employees. PageNumber: {}, PageSize: {}, SortDirection: {}, SortField: {}",
                    pageNumber, pageSize, sortDirection, sortField);
            return ResponseEntity.badRequest().build();

        } catch (Exception ex) {
            // Log internal server error and return an internal server error response
            logger.error("Internal server error while fetching employees. PageNumber: {}, PageSize: {}, SortDirection: {}, SortField: {}",
                    pageNumber, pageSize, sortDirection, sortField, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}