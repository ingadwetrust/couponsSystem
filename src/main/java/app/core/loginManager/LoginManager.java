package app.core.loginManager;

//@Component
//public class LoginManager {
//
//	@Autowired
//	private ConfigurableApplicationContext ctx;
//
//	public enum ClientType {
//		ADMINISTRATOR, COMPANY, CUSTOMER;
//	}
//
//	private boolean isLogged;
//
//	public ClientService login(String email, String password, ClientType clientType) {
//		if (clientType != null) {
//			switch (clientType) {
//			case ADMINISTRATOR:
//				// admin login
//				ClientService adminService = ctx.getBean(AdminService.class);
//				if (adminService.login(email, password)) {
//					System.out.println();
//					System.out.println("===============================================================");
//					System.out.println("You are logged in as admin");
//					System.out.println("===============================================================");
//					System.out.println();
//					isLogged = true;
//					return adminService;
//				}
//				break;
//			case COMPANY:
//
//				// login company
//				ClientService companyService = ctx.getBean(CompanyService.class);
//				if (companyService.login(email, password)) {
//					System.out.println();
//					System.out.println("===============================================================");
//					System.out.println("company " + email + " logged in");
//					System.out.println("===============================================================");
//					System.out.println();
//					isLogged = true;
//					return companyService;
//				} else {
//					System.err.println("no company with this email and password");
//				}
//				break;
//			case CUSTOMER:
//
//				// login customer
//				ClientService customerService = ctx.getBean(CustomerService.class);
//				if (customerService.login(email, password)) {
//					System.out.println("customer " + email + " logged in");
//					isLogged = true;
//					return customerService;
//
//				} else {
//					System.err.println("no customer with this email and password");
//				}
//				break;
//
//			default:
//				return null;
//
//			}
//		}
//		return null;
//	}
//
//	public boolean isLogged() {
//		return isLogged;
//	}
//
//	public void setLogged(boolean isLogged) {
//		this.isLogged = isLogged;
//	}
//
//}
