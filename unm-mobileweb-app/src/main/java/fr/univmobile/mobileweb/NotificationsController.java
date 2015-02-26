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
		
		RestTemplate template = restTemplate();
		
		//___________________________________________________________________________________________________________________________________
		//temporary for testing, delete later
		if (!hasSessionAttribute("univ")) {
			University univObj = restTemplate().getForObject(jsonUrl + "/universities/ " + 13, University.class);
			setSessionAttribute("univ", univObj);
		}
		//___________________________________________________________________________________________________________________________________
		
		
		if (!hasSessionAttribute("univ")) {

			sendRedirect(getBaseURL()+"/");
			return null;
		}  else {
			
			User user = null;
			if (!hasSessionAttribute("currentUser")) {
				//redirect to home or previous page
				sendRedirect(getBaseURL() + "/login");
				return null;
			} else {
				user = getSessionAttribute("currentUser", User.class);
				userId = user.getId();
			}
			
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
			
			//set new notification read date for the user
			if (notificationsList.length > 0) {
				template.getForObject(jsonUrl + "/notifications/lastRead?userId=" + userId + "&notificationId=" + notificationsList[0].getId() , NotificationEmbedded.class);
			}
			
			
			//provide all attributes below
			
			//menu attributes
			setAttribute("universityLogo", getUniversityLogo());
			setAttribute("university", getUniversity());
			setAttribute("menuMS", getMenuItems(jsonUrl, "MS"));
			setAttribute("menuTT", getMenuItems(jsonUrl, "TT"));
			setAttribute("menuMU", getMenuItems(jsonUrl, "MU"));
			
			//notifications attributes
			setAttribute("notificationsList", notificationsList);
			
			return new View("notifications.jsp");
		}		
	}
}