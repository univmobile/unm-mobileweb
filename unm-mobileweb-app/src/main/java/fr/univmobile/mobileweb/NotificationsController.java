package fr.univmobile.mobileweb;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import fr.univmobile.mobileweb.models.BookmarkEmbedded;
import fr.univmobile.mobileweb.models.Notification;
import fr.univmobile.mobileweb.models.NotificationEmbedded;
import fr.univmobile.mobileweb.models.University;
import fr.univmobile.mobileweb.models.User;
import fr.univmobile.web.commons.Paths;
import fr.univmobile.web.commons.View;

@Paths({ "notifications" })
public class NotificationsController extends AsbtractMobileWebJspController {
	
	protected final String jsonUrl;
	private int userId;
	
	/*******************************************************
	 * Constructor #1
	 * @param jsonUrl
	 *******************************************************/
	public NotificationsController(final String jsonUrl) {

		this.jsonUrl = jsonUrl;
	}


	/********************************************************
	 * This method is called automatically on /notifications view
	 ********************************************************/
	@Override
	public View action() throws IOException {
		
		RestTemplate template = restTemplate();
		
		if (!hasSessionAttribute("univ")) {

			sendRedirect(getBaseURL()+"/");
			return null;
		}  else {
			
			User user = null;
			if (hasSessionAttribute("currentUser")) {
				user = getSessionAttribute("currentUser", User.class);
				userId = user.getId();
			}
			
			// Get the list of notifications
			NotificationEmbedded notificationsContainer;
			if (user != null && user.getNotificationsReadDate() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ");
				notificationsContainer = template.getForObject(jsonUrl + "/notifications/search/findNotificationsForUniversitySince?universityId=" + getUniversity().getId() + "&since=" + sdf.format(user.getNotificationsReadDate()) + "&size=200", NotificationEmbedded.class);
			} else {
				notificationsContainer = template.getForObject(jsonUrl + "/notifications/search/findNotificationsForUniversity?universityId=" + getUniversity().getId() + "&size=200", NotificationEmbedded.class);
			}
			
			Notification[] notificationsList = null;
			if (notificationsContainer._embedded != null) {
				//must be prevented somewhere else, because if notifications does not exist the view gonna be empty
				notificationsList = notificationsContainer._embedded.getNotifications();
			} else {
				notificationsList = new Notification[0];
			}
			
			//set new notification read date for the user
			if (user != null && notificationsList.length > 0) {
				HttpHeaders headers = new HttpHeaders();
				headers.add("Authentication-Token", getSessionAttribute("authenticationToken", String.class));
				HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
					
				restTemplate().exchange(jsonUrl + "/notifications/lastRead?userId=" + userId + "&notificationId=" + notificationsList[0].getId(), HttpMethod.GET, entity, NotificationEmbedded.class).getBody();

			}
			
			
			//provide all attributes below
			
			//menu attributes
			setAttribute("universityLogo", getUniversityLogo());
			setAttribute("university", getUniversity());
			setAttribute("menuMS", getMenuItems(jsonUrl, "MS"));
			setAttribute("menuAU", getMenuItems(jsonUrl, "AU"));
			setAttribute("menuTT", getMenuItems(jsonUrl, "TT"));
			setAttribute("menuMU", getMenuItems(jsonUrl, "MU"));
			setAttribute("currentAbsolutePath", getAbsolutePath());
			
			//notifications attributes
			setAttribute("notificationsList", notificationsList);
			
			return new View("notifications.jsp");
		}		
	}
}