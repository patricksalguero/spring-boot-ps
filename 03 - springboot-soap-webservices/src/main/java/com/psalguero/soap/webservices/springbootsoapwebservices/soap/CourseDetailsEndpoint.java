package com.psalguero.soap.webservices.springbootsoapwebservices.soap;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.patricksalguero.courses.CourseDetails;
import com.patricksalguero.courses.GetAllCourseDetailsRequest;
import com.patricksalguero.courses.GetAllCourseDetailsResponse;
import com.patricksalguero.courses.GetCourseDetailsRequest;
import com.patricksalguero.courses.GetCourseDetailsResponse;
import com.psalguero.soap.webservices.springbootsoapwebservices.soap.bean.Course;
import com.psalguero.soap.webservices.springbootsoapwebservices.soap.service.CourseDetailsService;

@Endpoint
public class CourseDetailsEndpoint {

	@Autowired
	private CourseDetailsService service;

	private static final String ROOT_NAMESPACE = "http://patricksalguero.com/courses";

	// request .-> GetCourseDetailsRequest
	// response .-> GetCourseDetailsResponse
	private static Logger LOG = LoggerFactory.getLogger(CourseDetailsEndpoint.class);

	// http://patricksalguero.com/courses
	// GetCourseDetailsRequest
	@PayloadRoot(namespace = ROOT_NAMESPACE, localPart = "GetCourseDetailsRequest")
	@ResponsePayload
	public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest courseDetails) {

		Course course = service.findById(courseDetails.getId());

		return mapCourseDetails(course);
	}

	// GetCourseDetailsRequest
	@PayloadRoot(namespace = ROOT_NAMESPACE, localPart = "GetAllCourseDetailsRequest")
	@ResponsePayload
	public GetAllCourseDetailsResponse processAllCourseDetailsRequest(
			@RequestPayload GetAllCourseDetailsRequest courseDetails) {

		List<Course> course = service.findAll();

		return mapCourseDetails(course);
	}

	private GetCourseDetailsResponse mapCourseDetails(Course course) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();

		response.setCourseDetails(mapCourse(course));

		return response;
	}

	private GetAllCourseDetailsResponse mapCourseDetails(List<Course> courses) {
		GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();

		for (Course c : courses) {
			response.getCourseDetails().add(mapCourse(c));
		}

		return response;
	}

	private CourseDetails mapCourse(Course course) {
		CourseDetails details = new CourseDetails();

		details.setDescription(course.getDescription());
		details.setId(course.getId());
		details.setName(course.getName());
		return details;
	}
}
