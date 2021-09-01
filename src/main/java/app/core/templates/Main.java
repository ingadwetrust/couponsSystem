package app.core.templates;

import org.springframework.web.client.RestTemplate;

public class Main {

	public static void main(String[] args) {
		try {
			RestTemplate rt = new RestTemplate();
			String url;
//			{ // GET ONE COMPANY
//				// set the url
//				url = "http://localhost:8080/admin/getOneCompany/?id=1";
//				// set the request and get a response
//				ResponseEntity<Company> responseEntity = rt.exchange(url, HttpMethod.GET, null, Company.class);
//				System.out.println(responseEntity);
//				Company comp = responseEntity.getBody();
//				System.out.println(comp);
//			}
//
//			{ // GET all COMPANIES
//				url = "http://localhost:8080/admin/getAllCompanies";
//				ParameterizedTypeReference<List<Company>> type = new ParameterizedTypeReference<List<Company>>() {
//				};
//				ResponseEntity<List<Company>> responseEntity = rt.exchange(url, HttpMethod.GET, null, type);
//				List<Company> list = responseEntity.getBody();
//				System.out.println(list);
//			}

//			{ // POST
//				url = "http://localhost:8080/admin/addCompany";
//				Company comp = new Company("templateName", "templateEmail", "templatePassword");
//				comp = rt.postForObject(new URI(url), comp, Company.class);
//				System.out.println(comp);
//
//			}

			{ // PUT
//				url = "http://localhost:8080/admin/updateCompany";
//				Company e = new Company(1, "John", "Engineer", "Password");
//				RequestEntity<Company> request = new RequestEntity<Company>(e, HttpMethod.PUT, new URI(url));
//				e = rt.exchange(request, Company.class).getBody();
//				System.out.println(e);
			}

			{ // DELETE
//				url = "http://localhost:8080/admin/deleteCompany/?id=9";
//				rt.delete(url);
			}

		} catch (Exception e) {
			System.out.println("================");
			System.out.println(e.getMessage());
			System.out.println("================");
		}

	}
}
