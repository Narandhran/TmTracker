/**
 * @author Balaji
 *
 *         08-Mar-2017 - Balaji creation ParseCSVFileExample.java
 */
package com.neemShade.TmTracker.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import com.neemShade.TmTracker.dto.ClubDto;
import com.neemShade.TmTracker.dto.UserDto;
import com.neemShade.TmTracker.pojo.Club;
import com.neemShade.TmTracker.pojo.User;
import com.neemShade.TmTracker.service.ClubService;
import com.neemShade.TmTracker.service.UserService;

/**
 * @author Balaji
 *
 */


@Component
@ComponentScan(basePackages = {"com.neemShade.TmTracker"})
public class ParseCSVFileExample {



    public static final String CUSTOMER_ID = "CustomerID";
    public static final String MEMBER_ID = "Member Id";
    public static final String NAME = "Name";
    public static final String EMAIL = "Email";
    public static final String HOME_PHONE = "Home";
    public static final String MOBILE = "Mobile";


	private UserService userService;
	   

	private ClubService clubService;


    public String filename;
//    public Map<String, Integer> headerMap = new HashMap<String, Integer>();
    public String[] headerFields;
    public List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    
    public Long clubId;
    public Club club;

    public static String[] parseCsvLine(String line) {
        // Create a pattern to match breaks
        Pattern p =
Pattern.compile(",(?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))");
        // Split input with the pattern
        String[] fields = p.split(line);
        for (int i = 0; i < fields.length; i++) {
            // Get rid of residual double quotes
            fields[i] = fields[i].replace("\"", "");
        }
        return fields;
    }

    /**
     * @param headerFields
     */
    private void fillInHeader(String[] headerFields) {
        this.headerFields = headerFields;

//        for (int i = 0; i < headerFields.length; i++) {
//            System.out.print(headerFields[i] + " ");
//        }
//        System.out.println(" ");
    }

    /**
     * @param fields
     */
    private void fillInDataOfSingleRecord(String[] fields) {
        Map<String, String> fieldMap = new HashMap<String, String>();
        for (int i = 0; i < fields.length; i++) {
            fieldMap.put(headerFields[i], fields[i]);
        }
        data.add(fieldMap);
    }


    public void collectData()
    {
        try {
            BufferedReader input = new
            BufferedReader(new FileReader(filename));
            String line = null;

            line = input.readLine();

            if(line == null)
            {
                input.close();
                throw new Exception("no records");
            }

            String[] headerFields = parseCsvLine(line);
            fillInHeader(headerFields);

            while (( line = input.readLine()) != null) {
                String[] fields = parseCsvLine(line);
                fillInDataOfSingleRecord(fields);
            }
            input.close();
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    /**
     * find the record[rowNum] and the specific field in that row
     * @param rowNum
     * @param desiredField
     * @return
     * @throws Exception
     */
    public String retrieveData(int rowNum, String desiredField) throws Exception
    {
        if(data.size() < rowNum)
            throw new Exception("invalid row number");

        Map<String, String> map = data.get(rowNum);

        if(!map.containsKey(desiredField))
            throw new Exception("field not available");

        return map.get(desiredField);
    }

    /**
     *
     */
    private void printData() {


        for (Map<String, String> map : data) {
            if(map.containsKey(CUSTOMER_ID))
                System.out.print(map.get(CUSTOMER_ID) + "-");
            if(map.containsKey(MEMBER_ID))
                System.out.print(map.get(MEMBER_ID) + "-");
            System.out.print(map.get(NAME) + "-");
            System.out.print(map.get(EMAIL) + "-");
            System.out.print(map.get(HOME_PHONE) + "-");
            System.out.println(map.get(MOBILE) + "-");
        }
    }
    
    public void storeData()
    {
    	List<UserDto> userDtos = new ArrayList<UserDto>();
    	
    	for (Map<String, String> map : data) {
    		User user = new User();
    		
            if(map.containsKey(CUSTOMER_ID))
                ;
            if(map.containsKey(MEMBER_ID))
            	;
            	
            
            map.put(HOME_PHONE, UserDto.purifier(map.get(HOME_PHONE)));
            map.put(MOBILE, UserDto.purifier(map.get(MOBILE)));
            
            user.setUserName(map.get(NAME));
            user.setEmail(map.get(EMAIL));
            String phone = map.get(HOME_PHONE) == null || "".equals(map.get(HOME_PHONE)) ? 
            		map.get(MOBILE) : map.get(HOME_PHONE);
            user.setPhone(phone);
            
            UserDto userDto = userService.convertToDTO(user);
            userDtos.add(userDto);
    	}
    	
    	ClubDto clubDto = clubService.convertToDTO(club);
    	List<String> statusMsgs = userService.createUsers(userDtos, clubDto);
    	
    	displayResult(statusMsgs);
    }

    
    /**
	 * @param statusMsgs
	 */
	private void displayResult(List<String> statusMsgs) {
		
		for (int i = 0; i < 2; i++) {
			for (String msg : statusMsgs) {
				
				if(msg == null)
					continue;
				
				if(i == 0 ^ msg.startsWith("Success"))
					System.out.println(msg);
			}
		}
		
	}

	public void process() 
    {
    	collectData();
//      parseCSVFileExample.printData();
    
//    	this.club = findClub(clubId);
    	storeData();
    }
    
    /**
	 * @param clubId2
	 */
//	private Club findClub(Long clubId) {
////		return clubService.findClubById(clubId);
//	}
	
	@Autowired
	private static ApplicationContext _applicationContext;

	public static void main1(String[] args) {
		
		
		
		ParseCSVFileExample parseCSVFileExample = _applicationContext.getBean(ParseCSVFileExample.class);

//        ParseCSVFileExample parseCSVFileExample = new ParseCSVFileExample();
        parseCSVFileExample.setFilename("D:\\jbalaji\\personal\\neemShade\\software\\tmTracker\\MembershipRoster.csv");
        parseCSVFileExample.setClubId(2l);
        parseCSVFileExample.process();
    }




    public String getFilename() {
        return filename;
    }


    public void setFilename(String filename) {
        this.filename = filename;
    }

	public Long getClubId() {
		return clubId;
	}

	public void setClubId(Long clubId) {
		this.clubId = clubId;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ClubService getClubService() {
		return clubService;
	}

	public void setClubService(ClubService clubService) {
		this.clubService = clubService;
	} 
	
}
