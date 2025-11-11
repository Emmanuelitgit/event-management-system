package booking_service.util;
import booking_service.dto.PaginationPayload;
import booking_service.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Slf4j
@Component
public class AppUtils {


    /**
     * This method is used to handle all responses in the application.
     * @param message
     * @param status
     * @return responseDto object
     * @auther Emmanuel Yidana
     * @createdAt 16h April 2025
     */
    public static ResponseDTO getResponseDto(String message, HttpStatus status){
        ResponseDTO responseDto = new ResponseDTO();
        responseDto.setMessage(message);
        responseDto.setDate(ZonedDateTime.now());
        responseDto.setStatusCode(status.value());
        return responseDto;
    }

    /**
     * This method is used to handle all responses in the application.
     * @param message
     * @param status
     * @param data
     * @return responseDto object
     * @auther Emmanuel Yidana
     * @createdAt 16h April 2025
     */
    public static ResponseDTO getResponseDto(String message, HttpStatus status, Object data){
        if(data==null){
            ResponseDTO responseDto = getResponseDto(message, status);
            return responseDto;
        }
        ResponseDTO responseDto = new ResponseDTO();
        responseDto.setMessage(message);
        responseDto.setDate(ZonedDateTime.now());
        responseDto.setStatusCode(status.value());
        responseDto.setData(data);
        return responseDto;
    }


    /**
     * This method is used to get user full name.
     * @param first
     * @param last
     * @return responseDto object
     * @auther Emmanuel Yidana
     * @createdAt 16h April 2025
     */
    public static String getFullName(String first, String last){
        return first + " " + " " + last;
    }

    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final String DEFAULT_PAGE_SORT = "createdAt";
    public static final String DEFAULT_PAGE_SORT_DIR = "desc";

    /**
     * This method is used to set or handle pagination items.
     * @param paginationPayload
     * @return responseDto object
     * @auther Emmanuel Yidana
     * @createdAt 16h April 2025
     */
    public static Pageable getPageRequest(PaginationPayload paginationPayload){
        return PageRequest.of(paginationPayload.getPage()-1, paginationPayload.getSize());
    }

    /**
     * This method is used to get authenticated username.
     * @return username string
     * @auther Emmanuel Yidana
     * @createdAt 19h May 2025
     */
    public static String getAuthenticatedUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    /**
     * This method is used to get authenticated user role.
     * @return role string
     * @auther Emmanuel Yidana
     * @createdAt 19th May 2025
     */
    public String getAuthenticatedUserRole(){
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .filter(item->Character.isUpperCase(item.getAuthority().charAt(0)))
                .findFirst()
                .get()
                .getAuthority();
    }

    /**
     * @description This method is used to round values to their nearest decimal points
     * @param value The value to be rounded
     * @param decimalPlaces The decimal place to round the value to
     * @return The rounded value
     */
    public static float roundNumber(float value, int decimalPlaces) {
        double scale = Math.pow(10, decimalPlaces);
        return (float) (Math.round(value * scale) / scale);
    }

    /**
     * @description a method to convert a string date to local date on the (yyyy-MM-dd) date format
     * @param startDate
     * @return
     */
    public static LocalDate convertStringToLocalDateTime(String startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(startDate, formatter);

    }

    public static final ExampleMatcher SEARCH_CONDITION_MATCH_ALL = ExampleMatcher.matchingAll()
            .withMatcher("price", exact())
            .withIgnorePaths("id", "createdBy", "updatedBy", "createdAt", "updatedAt")
            .withMatcher("name", contains().ignoreCase());

    public static final ExampleMatcher SEARCH_CONDITION_MATCH_ANY = ExampleMatcher.matchingAny()
            .withMatcher("price", exact())
            .withIgnorePaths("id", "createdBy", "updatedBy", "createdAt", "updatedAt")
            .withMatcher("name", contains().ignoreCase());
}
