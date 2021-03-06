package services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import dao.UserDAO;
import model.User;
import spark.Request;
import spark.Response;

public class LoginService {
    public Object tryLogin(Request request, Response response) {
        boolean status = false;
        User[] users = UserDAO.getUsers();
        
        String email = request.queryParams("email");
        String password = request.queryParams("password");
        
        MessageDigest md;
		String newPass = "";
		try {
			md = MessageDigest.getInstance("MD5");
			BigInteger hash = new BigInteger(1, md.digest(password.getBytes()));
			newPass = hash.toString(16);
		} catch (NoSuchAlgorithmException e) {}

        
        for(User u : users) {
            if((u.getEmail().equals(email)) && (u.getHashPassword().equals(newPass))) {
                status = true;
                response.status(200);
                response.header("Content-Type", "application/xml");
    			response.header("Content-Encoding", "UTF-8");

    			return "<user>\n" + "\t<id>" + u.getUser_id() + "</id>\n" + "\t<username>" + u.getUsername()
    					+ "</username>\n" + "\t<userType>" + u.getUser_type() + "</userType>\n" + "\t<userPhoto>"
    					+ u.getPhoto_path() + "</userPhoto>\n" + "\t<email>" + u.getEmail() + "</email>\n"
    					+ "\t<profileName>" + u.getProfile_name() + "</profileName>\n" + "\t<localization>"
    					+ u.getProfile_localization() + "</localization>\n" + "\t<description>"
    					+ u.getProfile_description() + "</description>\n" + "</user>\n";
            }
        }
        
        response.status(404);
        return status;
    }
}
