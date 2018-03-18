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

import com.uniovi.services.DatosEjemplo;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.utils.SeleniumUtils;

//Ordenamos las pruebas por el nombre del mÃ©todo 
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedsocialnatorTests {

	// En Windows (Debe ser la versiÃ³n 46.0 y desactivar las actualizacioens
	// automÃ¡ticas)):
	// static String PathFirefox = "C:\\Path\\FirefoxPortable.exe";
	static String PathFirefox = "P:\\SDI\\P5\\Firefox46.0.win\\Firefox46.win\\FirefoxPortable.exe";
	private static DatosEjemplo datosEjemplo = new DatosEjemplo();

	// ComÃºn a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox);
	static String URL = "http://localhost:8092";

	public static WebDriver getDriver(String PathFirefox) {
		// Firefox (VersiÃ³n 46.0) sin geckodriver para Selenium 2.x.
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	//	@BeforeClass
	//	public static void resetDatabase() {
	//		driver.navigate().to(URL);
	//		// Vamos al formulario de logueo.
	//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
	//		// Rellenamos el formulario
	//		PO_LoginView.fillForm(driver, "99999993D", "123456");
	//		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
	//	}

	@Test
	public void test() {
		//		RegVal();
		//		RegInval();
		//		PR03();
		//		PR04();
		//		PR05();
		//		PR07();
		//		PR08();
		//		PR09();
		//		PR10();
		//		PR11();
		//		PR12();
		//		PR13();
		//		PR14();
		//		PR15();

	}

	// Antes de cada prueba se navega al URL home de la aplicaciÃ³nn
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// DespuÃ©s de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
		//Cargar los usuarios antes de comenzar los tests ????????
		//datosEjemplo.init();
	}

	// Al finalizar la Ãºltima prueba
	@AfterClass
	static public void end() { // Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	/*
	 * CASOS DE PRUEBA
	 */
	//1.1 [RegVal] Registro de Usuario con datos válidos.
	@Test
	public void RegVal() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "Josefo@gmail.com", "Josefo", "Perez", "123456", "123456");
		PO_View.checkElementLocale(driver, "usuariosTitle", "text.usuariosDisponibles"); //No funciona, faltaria coincidir el campo con la internacionalizacion referent al id
	}

	//1.2 [RegInval] Registro de Usuario con datos inválidos (repetición de contraseña invalida).
	@Test
	public void RegInval() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "Josefo@gmail.com", "Josefo", "Perez", "12345", "12345");
		PO_View.checkElementLocale(driver, "text-danger", "*{password}"); //No funciona, faltaria coincidir el campo con la internacionalizacion referent al id

	}

	//2.1 [InVal] Inicio de sesión con datos válidos.
	// home
	@Test
	public void InVal() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Josefo@gmail.com", "123456");
		//Comprueba que vea la lista de usuarios
		PO_View.checkElementLocale(driver, "usuariosTitle", "text.usuariosDisponibles");


		//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		//		PO_View.checkElement(driver, "usuariosTitle", "#{text.usuariosDisponibles}");
	}

	//2.2 [InInVal] Inicio de sesión con datos inválidos (usuario no existente en la aplicación).
	// EspaÃ±ol
	@Test
	public void InInVal() {
		//		PO_HomeView.checkChangeIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(),
		//				PO_Properties.getENGLISH());
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Josefa@gmail.com", "123456");
		PO_View.checkElementLocale(driver, "login", "iniciar.sesion"); //Lo hace el validator asi que en la pagina no se puede comprobar el error
		//Se puede checkear si está algun elemento del login otra vez

	}

	//3.1 [LisUsrVal] Acceso al listado de usuarios desde un usuario en sesión.
	@Test
	public void LisUsrVal() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Josefo@gmail.com", "123456");
		PO_View.checkElement(driver, "login", "Login");
		//LOGIN VALIDO

		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary"); //Mirar como se hace mediante href ya que no hay botón
		PO_View.checkElement(driver, "login", "Login");


	}

	//3.2 [LisUsrInVal] Intento de acceso con URL desde un usuario no identificado al listado de usuarios
	//desde un usuario en sesión. Debe producirse un acceso no permitido a vistas privadas
	@Test
	public void LisUsrInVal() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		driver.navigate().to("http://localhost:8092/user/list");
		//Nos deberia redirigir a la pagina del login
		PO_View.checkElementLocale(driver, "login", "iniciar.sesion");

	}

	//4.1 [BusUsrVal] Realizar una búsqueda valida en el listado de usuarios desde un usuario en sesión
	@Test
	public void BusUsrVal() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Josefo@gmail.com", "123456");
		//LOGIN VALIDO

		PO_PrivateView.searchUser(driver, "Zelda");


	}

	//4.2 [BusUsrInVal] Intento de acceso con URL a la búsqueda de usuarios desde un usuario no
	//identificado. Debe producirse un acceso no permitido a vistas privadas.
	@Test
	public void BusUsrInVal() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		driver.navigate().to("http://localhost:8092/user/list");
		//Nos deberia redirigir a la pagina del login
		PO_View.checkElementLocale(driver, "login", "iniciar.sesion");
	}



	//5.1 [InvVal] Enviar una invitación de amistad a un usuario de forma valida. 
	@Test
	public void PR10() {
		//LOGIN VALIDO
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Josefo@gmail.com", "123456");
		PO_View.checkElementLocale(driver, "usuariosTitle", "text.usuariosDisponibles");
		//LOGIN VALIDO

		PO_PrivateView.searchUser(driver, "Zelda"); //??
		//Agregar Zelda como amigo
		By boton = By.id("btnZelda");	
		if(boton != null)
			driver.findElement(boton).click();

	}

	//5.2 [InvInVal] Enviar una invitación de amistad a un usuario al que ya le habíamos invitado la invitación
	//previamente. No debería dejarnos enviar la invitación, se podría ocultar el botón de enviar invitación o
	//notificar que ya había sido enviada previamente.
	@Test
	public void InvInVal() {
		//LOGIN VALIDO
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Josefo@gmail.com", "123456");
		PO_View.checkElementLocale(driver, "usuariosTitle", "text.usuariosDisponibles");
		//LOGIN VALIDO
		
		PO_PrivateView.searchUser(driver, "Zelda"); //??

	}

		//6.1 [LisInvVal] Listar las invitaciones recibidas por un usuario, realizar la comprobación con una lista
		//que al menos tenga una invitación recibida.
		@Test
		public void LisInvVal() {
			//LOGIN VALIDO
			PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
			// Vamos al formulario de logueo.
			PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
			// Rellenamos el formulario
			PO_LoginView.fillForm(driver, "Josefo@gmail.com", "123456");
			PO_View.checkElementLocale(driver, "usuariosTitle", "text.usuariosDisponibles");
			//LOGIN VALIDO


			
			//NECESITAMOS AGREGARLE A MANO UNA PETICION RECIBIDA AL USUARIO Josefo@gmail.com
	
			
			PO_NavView.checkPeticionesRecibidas(driver);
		}
	
	//	//7.1 [AcepInvVal] Aceptar una invitación recibida
	//	// = Nota A2.
	//	// P13. Ver la lista de Notas.
	//	@Test
	//	public void PR13() {
	//		// Vamos al formulario de logueo.
	//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
	//		// Rellenamos el formulario
	//		PO_LoginView.fillForm(driver, "99999990A", "123456");
	//		// COmprobamos que entramos en la pagina privada de Alumno
	//		PO_View.checkElement(driver, "text", "Notas del usuario");
	//		SeleniumUtils.esperarSegundos(driver, 1);
	//		// Contamos las notas
	//		By enlace = By.xpath("//td[contains(text(), 'Nota A2')]/following-sibling::*[2]"); // Para evitar esto lo mejor
	//																							// es poner id y nombre
	//		driver.findElement(enlace).click();
	//		SeleniumUtils.esperarSegundos(driver, 1);
	//		// Esperamos por la ventana de detalle
	//		PO_View.checkElement(driver, "text", "Detalles de la nota");
	//		SeleniumUtils.esperarSegundos(driver, 1);
	//		// Ahora nos desconectamos
	//		PO_PrivateView.clickOption(driver, "logout", "text", "IdentifÃ­cate");
	//	}
	//
	//	//8.1 [ListAmiVal] Listar los amigos de un usuario, realizar la comprobación con una lista que al menos tenga un amigo
	//	// encapsularse mejor ...
	//	@Test
	//	public void PR14() {
	//		// Vamos al formulario de logueo.
	//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
	//
	//		// Rellenamos el formulario
	//		PO_LoginView.fillForm(driver, "99999993D", "123456");
	//		// COmprobamos que entramos en la pagina privada del Profesor
	//		PO_View.checkElement(driver, "text", "99999993D");
	//		// Pinchamos en la opciÃ³n de menu de Notas: //li[contains(@id, 'marks-menu')]/a
	//		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'marks-menu')]/a");
	//		elementos.get(0).click();
	//		// Esperamos a aparezca la opciÃ³n de aÃ±adir nota: //a[contains(@href,
	//		// 'mark/add')]
	//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/add')]");
	//		// Pinchamos en agregar Nota.
	//		elementos.get(0).click();
	//		// Ahora vamos a rellenar la nota. //option[contains(@value, '4')]
	//		PO_PrivateView.fillFormAddMark(driver, 3, "Nota Nueva 1", "8");
	//		// Esperamos a que se muestren los enlaces de paginaciÃ³n la lista de notas
	//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
	//		// Nos vamos a la Ãºltima pÃ¡gina
	//		elementos.get(3).click();
	//		// Comprobamos que aparece la nota en la pagina
	//		elementos = PO_View.checkElement(driver, "text", "Nota Nueva 1");
	//		// Ahora nos desconectamos
	//		PO_PrivateView.clickOption(driver, "logout", "text", "IdentifÃ­cate");
	//	}
	//
	//	//9.1 [PubVal] Crear una publicación con datos válidos. 
	//	// Nueva 1.
	//	// PRN. Ver la lista de Notas.
	//	@Test
	//	public void PR15() {
	//		// Vamos al formulario de logueo.
	//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
	//		// Rellenamos el formulario
	//		PO_LoginView.fillForm(driver, "99999993D", "123456");
	//		// COmprobamos que entramos en la pagina privada del Profesor
	//		PO_View.checkElement(driver, "text", "99999993D");
	//		// Pinchamos en la opciÃ³n de menu de Notas: //li[contains(@id, 'marks-menu')]/a
	//		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'marks-menu')]/a");
	//		elementos.get(0).click();
	//		// Pinchamos en la opciÃ³n de lista de notas.
	//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/list')]");
	//		elementos.get(0).click();
	//		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
	//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'pagelink')]");
	//		// Nos vamos a la Ãºltima pÃ¡gina
	//		elementos.get(3).click();
	//		// Esperamos a que aparezca la Nueva nota en la ultima pagina
	//
	//		// Y Pinchamos en el enlace de borrado de la Nota "Nota Nueva 1"
	//		// //td[contains(text(), 'Nota Nueva
	//		// 1')]/following-sibling::*/a[contains(text(), 'mark/delete')]"
	//		elementos = PO_View.checkElement(driver, "free",
	//				"//td[contains(text(), 'Nota Nueva 1')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
	//		elementos.get(0).click();
	//		// Volvemos a la Ãºltima pagina
	//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'pagelink')]");
	//		elementos.get(3).click();
	//		// Y esperamos a que NO aparezca la ultima "Nueva Nota 1"
	//		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Nota Nueva 1", PO_View.getTimeout());
	//		// Ahora nos desconectamos
	//		PO_PrivateView.clickOption(driver, "logout", "text", "IdentifÃ­cate");
	//	}

	//	//10.1 [LisPubVal] Acceso al listado de publicaciones desde un usuario en sesión.
	//	// Nueva 1.
	//	// PRN. Ver la lista de Notas.
	//	@Test
	//	public void PR15() {
	//		// Vamos al formulario de logueo.
	//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
	//		// Rellenamos el formulario
	//		PO_LoginView.fillForm(driver, "99999993D", "123456");
	//		// COmprobamos que entramos en la pagina privada del Profesor
	//		PO_View.checkElement(driver, "text", "99999993D");
	//		// Pinchamos en la opciÃ³n de menu de Notas: //li[contains(@id, 'marks-menu')]/a
	//		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'marks-menu')]/a");
	//		elementos.get(0).click();
	//		// Pinchamos en la opciÃ³n de lista de notas.
	//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/list')]");
	//		elementos.get(0).click();
	//		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
	//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'pagelink')]");
	//		// Nos vamos a la Ãºltima pÃ¡gina
	//		elementos.get(3).click();
	//		// Esperamos a que aparezca la Nueva nota en la ultima pagina
	//
	//		// Y Pinchamos en el enlace de borrado de la Nota "Nota Nueva 1"
	//		// //td[contains(text(), 'Nota Nueva
	//		// 1')]/following-sibling::*/a[contains(text(), 'mark/delete')]"
	//		elementos = PO_View.checkElement(driver, "free",
	//				"//td[contains(text(), 'Nota Nueva 1')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
	//		elementos.get(0).click();
	//		// Volvemos a la Ãºltima pagina
	//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'pagelink')]");
	//		elementos.get(3).click();
	//		// Y esperamos a que NO aparezca la ultima "Nueva Nota 1"
	//		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Nota Nueva 1", PO_View.getTimeout());
	//		// Ahora nos desconectamos
	//		PO_PrivateView.clickOption(driver, "logout", "text", "IdentifÃ­cate");
	//	}
}
