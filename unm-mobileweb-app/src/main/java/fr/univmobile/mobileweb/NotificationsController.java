package fr.univmobile.mobileweb;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.springframework.web.client.RestTemplate;

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
		
		userId = 0;
		RestTemplate template = restTemplate();
		
		//___________________________________________________________________________________________________________________________________
		//temporary for testing, delete later
		if (!hasSessionAttribute("univ")) {
			University univObj = restTemplate().getForObject(jsonUrl + "/universities/ " + 13, University.class);
			setSessionAttribute("univ", univObj);
		}
		userId = 1;
		//___________________________________________________________________________________________________________________________________
		
		User user = null;
		if (userId != 0) {
			user = template.getForObject(jsonUrl + "/users/ " + userId, User.class);
		}
		
		if (!hasSessionAttribute("univ") || user == null) {

			sendRedirect(getBaseURL());
			return null;
		}  else {
			
			// Get the list of notifications
			NotificationEmbedded notificationsContainer;
			if (user.getNotificationsReadDate() != null) {
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
			
			//provide all attributes below
			
			//menu attributes
			setAttribute("universityLogo", getUniversityLogo());
			setAttribute("university", getUniversity());
			setAttribute("menuMS", getMenuItems(jsonUrl, "MS"));
			setAttribute("menuTT", getMenuItems(jsonUrl, "TT"));
			setAttribute("menuMU", getMenuItems(jsonUrl, "MU"));
			
			//news attributes
			setAttribute("notificationsList", notificationsList);
			
			return new View("notifications.jsp");
		}		
	}
}