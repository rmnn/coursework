package ru.spbu.ageevd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateRating {

	public UpdateRating() {

	}

	public String update(String name, String password, int rating)
			throws IOException {
		return requestGET("http://guess-the-city.appspot.com/visit?user="
				+ name + "&passwd=" + password + "&rating="
				+ Integer.toString(rating));
	}

	public String register(String name, String password) throws IOException {
		return requestGET("http://guess-the-city.appspot.com/visit?user="
				+ name + "&passwd=" + password);
	}

	public String requestGET(String address) {
		String result = "";
		try {
			URL url = new URL(address);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestProperty("User-Agent", "Java bot");

			conn.connect();

			int code = conn.getResponseCode();

			if (code == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));

				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					result += inputLine;
				}

				in.close();
			}

			conn.disconnect();
			conn = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
