package com.uniovi.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

//import com.uniovi.services.DatosEjemplo;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_UsersListView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.utils.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método 
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedsocialnatorTests {

	// En Windows (Debe ser la versión 46.0 y desactivar las actualizacioens
	// automáticas)):
	// static String PathFirefox = "C:\\Path\\FirefoxPortable.exe";

	// Path Joni
	// static String PathFirefox =
	// "P:\\SDI\\P5\\Firefox46.0.win\\Firefox46.win\\FirefoxPortable.exe";

	// Path Javi MSI
	static String PathFirefox = "D:\\Programs\\Firefox46.win\\FirefoxPortable.exe";

	// Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox);

	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox) {
		// Firefox (Versión 46.0) sin geckodriver para Selenium 2.x.
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	@BeforeClass
	public static void resetDatabaseToDefault() {
		driver.navigate().to(URL);
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "id", "login");
		// Rellenamos el formulario con admin
		PO_LoginView.fillForm(driver, "admin@correo.es", "123456");
		PO_NavView.clickOptionConCriterio(driver, "userAdminMenu", "id", "userAdminMenu");
		PO_NavView.clickOption(driver, "id", "restoreDBDefault");
		PO_NavView.clickLogout(driver);

	}

	// Antes de cada prueba se navega al URL home de la aplicaciónn
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {

	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() { // Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	/*
	 * CASOS DE PRUEBA
	 */
	// 1.1 [RegVal] Registro de Usuario con datos válidos.
	@Test
	public void T01_1_RegVal() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		PO_HomeView.clickOption(driver, "signup", "id", "signup");
		PO_RegisterView.fillForm(driver, "JaviTest@email.com", "JaviTest", "Castro", "123456", "123456");

		// Se ha metido el email mal, deberia estar ese campo de error
		PO_View.checkElement(driver, "id", "bienvenidaUser");

	}

	// 1.2 [RegInval] Registro de Usuario con datos inv�lidos (repetici�n de
	// contrase�a invalida).
	@Test
	public void T01_2_RegInval() {

		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		PO_HomeView.clickOption(driver, "signup", "id", "signup");
		PO_RegisterView.fillForm(driver, "preuba@emailmal", "Prueba", "Fernandez", "123456", "123456");
		SeleniumUtils.esperarSegundos(driver, 1);
		// Se ha metido el email mal, deberia estar ese campo de error
		PO_View.checkElement(driver, "id", "emailMal");

	}

	// 2.1 [InVal] Inicio de sesi�n con datos v�lidos.
	// home
	@Test
	public void T02_1_InVal() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "id", "login");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "javier@correo.es", "123456");
		// Comprueba que vea la lista de usuarios
		PO_View.checkElement(driver, "id", "bienvenidaUser");

	}

	// 2.2 [InInVal] Inicio de sesión con datos inválidos (usuario no existente en
	// la aplicación).
	// Español
	@Test
	public void T02_2_InInVal() {
		// PO_HomeView.checkChangeIdiom(driver, "btnSpanish", "btnEnglish",
		// PO_Properties.getSPANISH(),
		// PO_Properties.getENGLISH());
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "id", "login");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Josefa@gmail.com", "123456");
		// SeleniumUtils.EsperaCargaPagina(driver, "id", "login", 2);
		// Se puede checkear si est� algun elemento del login otra vez
		PO_View.checkElement(driver, "id", "errorLogin");

	}

	// 3.1 [LisUsrVal] Acceso al listado de usuarios desde un usuario en sesion
	@Test
	public void T03_1_LisUsrVal() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "id", "login");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "javier@correo.es", "123456");
		// SeleniumUtils.EsperaCargaPagina(driver, "id", "login", 2);
		// LOGIN VALIDO
		// Una vez dentro clickamos en home y que nos lleve a la lista de usuarios del
		// sistema.
		PO_HomeView.clickOption(driver, "id", "userListPage");

		// Se comprueba que esta el texto de la pagina que dice que esos son los
		// usuarios que hay en el sistema.
		PO_View.checkElement(driver, "id", "parrafoUsuariosSistema");

	}

	// 3.2 [LisUsrInVal] Intento de acceso con URL desde un usuario no identificado
	// al listado de usuarios
	// desde un usuario en sesi�n. Debe producirse un acceso no permitido a vistas
	// privadas
	@Test
	public void T03_2_LisUsrInVal() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		driver.navigate().to("http://localhost:8090/user/list");
		// Nos deberia redirigir a la pagina del login
		// SeleniumUtils.EsperaCargaPagina(driver, "id", "login", 2);
		PO_View.checkElement(driver, "id", "identificateField");
	}

	// 4.1 [BusUsrVal] Realizar una busqueda valida en el listado de usuarios desde
	// un usuario en sesion
	@Test
	public void T04_1_BusUsrVal() {
		// Login valido
		iniciarSesion("javier@correo.es", "123456");
		PO_PrivateView.searchUser(driver, "Zelda");
		PO_View.checkElement(driver, "id", "filaDeZeldaValles@default.es");

	}

	// 4.2 [BusUsrInVal] Intento de acceso con URL a la busqueda de usuarios desde
	// un usuario no identificado. Debe producirse un acceso no permitido a vistas
	// privadas.
	@Test
	public void T04_2_BusUsrInVal() {
		driver.navigate().to("http://localhost:8090/user/list?searchText=Zelda");
		// Nos deberia redirigir a la pagina del login
		PO_View.checkElement(driver, "id", "identificateField");
	}

	// 5.1 [InvVal] Enviar una invitacion de amistad a un usuario de forma valida.
	@Test
	public void T05_1_InvVal() {
		// Login valido
		iniciarSesion("javier@correo.es", "123456");
		PO_PrivateView.clickOption(driver, "id", "btnAshRodriguez@default.es");
		assertTrue(PO_View.checkElementExists(driver, "id", "btnAshRodriguez@default.es") == false);
		PO_NavView.accederPeticionesEnviadas(driver);
		// btnAshRodriguez@default.es
		PO_View.checkElement(driver, "id", "filaDeAshRodriguez@default.es");
	}

	/*
	 * 5.2 [InvInVal] Enviar una invitacion de amistad a un usuario al que ya le
	 * habiamos invitado la invitacion previamente. No debera dejarnos enviar la
	 * invitacion, se podra ocultar el boton de enviar invitacion notificar que ya
	 * habra sido enviada previamente.
	 * 
	 * TAMBIEN COMPRUEBA EL ACCESO POR URL
	 */
	@Test
	public void T05_2_InvInVal() {

		iniciarSesion("javier@correo.es", "123456");

		// Compruebo que ya le he enviado la peticion
		PO_NavView.accederPeticionesEnviadas(driver);
		// btnAshRodriguez@default.es
		PO_View.checkElement(driver, "id", "filaDeAshRodriguez@default.es");

		// Vuelvo a la pagina de listar usuarios y compruebo que no este el boton de ese
		// usuario al que le envie la peticion
		PO_PrivateView.clickOption(driver, "id", "userListPage");
		assertTrue(PO_View.checkElementExists(driver, "id", "btnAshRodriguez@default.es") == false);

		// /peticion/enviarPeticion?emailRecibe=JaviTest@email.com

		// Lo intentamos hacer desde la url
		driver.navigate().to("http://localhost:8090/peticion/enviarPeticion?emailRecibe=JaviTest@email.com");
		PO_NavView.accederPeticionesEnviadas(driver);
		assertTrue(PO_View.countElements(driver, "id", "filaDeAshRodriguez") == 1);

	}

	// 6.1 [LisInvVal] Listar las invitaciones recibidas por un usuario, realizar la
	// comprobacion con una lista que al menos tenga una invitacion recibida.
	@Test
	public void T06_1_LisInvVal() {
		resetDatabaseToDefault();
		iniciarSesion("javier@correo.es", "123456");
		// Vamos a la pagina de usuarios por si acaso
		// PO_PrivateView.clickOption(driver, "id", "userListPage");
		PO_PrivateView.searchUser(driver, "joni");
		PO_PrivateView.clickOption(driver, "id", "btnjoni@correo.es");

		PO_NavView.clickLogout(driver);
		iniciarSesion("joni@correo.es", "123456");
		PO_NavView.accederPeticionesRecibidas(driver);
		PO_View.checkElement(driver, "id", "filaDejavier@correo.es");
	}

	//
	// 7.1 [AcepInvVal] Aceptar una invitaci�n recibida
	@Test
	public void T07_1_AcepInvVal() {
		iniciarSesion("joni@correo.es", "123456");
		PO_NavView.accederPeticionesRecibidas(driver);
		PO_PrivateView.clickOption(driver, "id", "btnjavier@correo.es");
		assertTrue(PO_View.checkElementExists(driver, "id", "filaDejavier@correo.es") == false);
	}
	// // = Nota A2.
	// // P13. Ver la lista de Notas.
	// @Test
	// public void PR13() {
	// // Vamos al formulario de logueo.
	// PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
	// // Rellenamos el formulario
	// PO_LoginView.fillForm(driver, "99999990A", "123456");
	// // COmprobamos que entramos en la pagina privada de Alumno
	// PO_View.checkElement(driver, "text", "Notas del usuario");
	// SeleniumUtils.esperarSegundos(driver, 1);
	// // Contamos las notas
	// By enlace = By.xpath("//td[contains(text(), 'Nota
	// A2')]/following-sibling::*[2]"); // Para evitar esto lo mejor
	// // es poner id y nombre
	// driver.findElement(enlace).click();
	// SeleniumUtils.esperarSegundos(driver, 1);
	// // Esperamos por la ventana de detalle
	// PO_View.checkElement(driver, "text", "Detalles de la nota");
	// SeleniumUtils.esperarSegundos(driver, 1);
	// // Ahora nos desconectamos
	// PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	// }
	//
	// //8.1 [ListAmiVal] Listar los amigos de un usuario, realizar la comprobaci�n
	// con una lista que al menos tenga un amigo
	// // encapsularse mejor ...
	// @Test
	// public void PR14() {
	// // Vamos al formulario de logueo.
	// PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
	//
	// // Rellenamos el formulario
	// PO_LoginView.fillForm(driver, "99999993D", "123456");
	// // COmprobamos que entramos en la pagina privada del Profesor
	// PO_View.checkElement(driver, "text", "99999993D");
	// // Pinchamos en la opción de menu de Notas: //li[contains(@id,
	// 'marks-menu')]/a
	// List<WebElement> elementos = PO_View.checkElement(driver, "free",
	// "//li[contains(@id, 'marks-menu')]/a");
	// elementos.get(0).click();
	// // Esperamos a aparezca la opción de añadir nota: //a[contains(@href,
	// // 'mark/add')]
	// elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,
	// 'mark/add')]");
	// // Pinchamos en agregar Nota.
	// elementos.get(0).click();
	// // Ahora vamos a rellenar la nota. //option[contains(@value, '4')]
	// PO_PrivateView.fillFormAddMark(driver, 3, "Nota Nueva 1", "8");
	// // Esperamos a que se muestren los enlaces de paginación la lista de notas
	// elementos = PO_View.checkElement(driver, "free", "//a[contains(@class,
	// 'page-link')]");
	// // Nos vamos a la última página
	// elementos.get(3).click();
	// // Comprobamos que aparece la nota en la pagina
	// elementos = PO_View.checkElement(driver, "text", "Nota Nueva 1");
	// // Ahora nos desconectamos
	// PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	// }
	//
	// //9.1 [PubVal] Crear una publicaci�n con datos v�lidos.
	// // Nueva 1.
	// // PRN. Ver la lista de Notas.
	// @Test
	// public void PR15() {
	// // Vamos al formulario de logueo.
	// PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
	// // Rellenamos el formulario
	// PO_LoginView.fillForm(driver, "99999993D", "123456");
	// // COmprobamos que entramos en la pagina privada del Profesor
	// PO_View.checkElement(driver, "text", "99999993D");
	// // Pinchamos en la opción de menu de Notas: //li[contains(@id,
	// 'marks-menu')]/a
	// List<WebElement> elementos = PO_View.checkElement(driver, "free",
	// "//li[contains(@id, 'marks-menu')]/a");
	// elementos.get(0).click();
	// // Pinchamos en la opción de lista de notas.
	// elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,
	// 'mark/list')]");
	// elementos.get(0).click();
	// // Esperamos a que se muestren los enlaces de paginacion la lista de notas
	// elementos = PO_View.checkElement(driver, "free", "//a[contains(@class,
	// 'pagelink')]");
	// // Nos vamos a la última página
	// elementos.get(3).click();
	// // Esperamos a que aparezca la Nueva nota en la ultima pagina
	//
	// // Y Pinchamos en el enlace de borrado de la Nota "Nota Nueva 1"
	// // //td[contains(text(), 'Nota Nueva
	// // 1')]/following-sibling::*/a[contains(text(), 'mark/delete')]"
	// elementos = PO_View.checkElement(driver, "free",
	// "//td[contains(text(), 'Nota Nueva
	// 1')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
	// elementos.get(0).click();
	// // Volvemos a la última pagina
	// elementos = PO_View.checkElement(driver, "free", "//a[contains(@class,
	// 'pagelink')]");
	// elementos.get(3).click();
	// // Y esperamos a que NO aparezca la ultima "Nueva Nota 1"
	// SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Nota Nueva 1",
	// PO_View.getTimeout());
	// // Ahora nos desconectamos
	// PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	// }

	// //10.1 [LisPubVal] Acceso al listado de publicaciones desde un usuario en
	// sesi�n.
	// // Nueva 1.
	// // PRN. Ver la lista de Notas.
	// @Test
	// public void PR15() {
	// // Vamos al formulario de logueo.
	// PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
	// // Rellenamos el formulario
	// PO_LoginView.fillForm(driver, "99999993D", "123456");
	// // COmprobamos que entramos en la pagina privada del Profesor
	// PO_View.checkElement(driver, "text", "99999993D");
	// // Pinchamos en la opción de menu de Notas: //li[contains(@id,
	// 'marks-menu')]/a
	// List<WebElement> elementos = PO_View.checkElement(driver, "free",
	// "//li[contains(@id, 'marks-menu')]/a");
	// elementos.get(0).click();
	// // Pinchamos en la opción de lista de notas.
	// elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,
	// 'mark/list')]");
	// elementos.get(0).click();
	// // Esperamos a que se muestren los enlaces de paginacion la lista de notas
	// elementos = PO_View.checkElement(driver, "free", "//a[contains(@class,
	// 'pagelink')]");
	// // Nos vamos a la última página
	// elementos.get(3).click();
	// // Esperamos a que aparezca la Nueva nota en la ultima pagina
	//
	// // Y Pinchamos en el enlace de borrado de la Nota "Nota Nueva 1"
	// // //td[contains(text(), 'Nota Nueva
	// // 1')]/following-sibling::*/a[contains(text(), 'mark/delete')]"
	// elementos = PO_View.checkElement(driver, "free",
	// "//td[contains(text(), 'Nota Nueva
	// 1')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
	// elementos.get(0).click();
	// // Volvemos a la última pagina
	// elementos = PO_View.checkElement(driver, "free", "//a[contains(@class,
	// 'pagelink')]");
	// elementos.get(3).click();
	// // Y esperamos a que NO aparezca la ultima "Nueva Nota 1"
	// SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Nota Nueva 1",
	// PO_View.getTimeout());
	// // Ahora nos desconectamos
	// PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
	// }

	private void iniciarSesion(String email, String pass) {
		// Vamos al formulario de logueo.
		PO_NavView.clickOption(driver, "id", "login");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, email, pass);
		// // Comprueba que vea la lista de usuarios
		// PO_View.checkElement(driver, "id", "bienvenidaUser");
	}
}
