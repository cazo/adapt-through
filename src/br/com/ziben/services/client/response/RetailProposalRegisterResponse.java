package br.com.ziben.services.client.response;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Classe que representa a de-serializa��o do XML da proposta.
 * 
 * @author Frederico Jabulka
 * 
 */
@XStreamAlias("RetailProposalRegister")
public class RetailProposalRegisterResponse {
	/**
	 * Contract (contrato) � string(9) c�digo do contrato.
	 * Par�metro obrigat�rio
	 */
	private String Contract;

	/**
	 * AnalysisStatus (statusAnalise) � string(1) 0 � Negado, 1 � Aprovado ou 2 � Mesa. Par�metro obrigat�rio.
	 */
	private String AnalysisStatus;

	/**
	 * AuthorizationCode (codAutorizacao) � string(10) C�digo de autoriza��o obtido da financeira. Par�metro opcional pode ser vazio caso o financiamento seja da pr�pria
	 * eletrozema.
	 */
	private String AuthorizationCode;

	/**
	 * OrderNumber (numeroPedido) � string(10) identificador do or�amento. Par�metro obrigat�rio.
	 */
	private String OrderNumber;

	/**
	 * PurchaseAmount (valorCompra) � number(14,5) valor da compra do contrato pela financeira. Se a pr�pria Eletrozema financiar o contrato ent�o ser� 0.
	 */
	private String PurchaseAmount;

	/**
	 * Return (retorno) � string(1) 0 indica erro, 1 sucesso. Par�metro obrigat�rio.
	 */
	private String Return;

	/**
	 * Message1 (mensagem1) � string(255) mensagem real de erro caso ocorra. Par�metro obrigat�rio.
	 */
	private String Message1;

	/**
	 * Message2 (mensagem2) � string(255) mensagem amig�vel para exibi��o ao usuario. Par�metro obrigat�rio.
	 */
	private String Message2;

	public String getContract() {
		return Contract;
	}

	public void setContract(String contract) {
		Contract = contract;
	}

	public String getAnalysisStatus() {
		return AnalysisStatus;
	}

	public void setAnalysisStatus(String analysisStatus) {
		AnalysisStatus = analysisStatus;
	}

	public String getAuthorizationCode() {
		return AuthorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		AuthorizationCode = authorizationCode;
	}

	public String getOrderNumber() {
		return OrderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		OrderNumber = orderNumber;
	}

	public String getPurchaseAmount() {
		return PurchaseAmount;
	}

	public void setPurchaseAmount(String purchaseAmount) {
		PurchaseAmount = purchaseAmount;
	}

	public String getReturn() {
		return Return;
	}

	public void setReturn(String return1) {
		Return = return1;
	}

	public String getMessage1() {
		return Message1;
	}

	public void setMessage1(String message1) {
		Message1 = message1;
	}

	public String getMessage2() {
		return Message2;
	}

	public void setMessage2(String message2) {
		Message2 = message2;
	}

	/**
	 * @param object
	 *            Objeto a ser serializado
	 * @return String com o XML representando o objeto serializado.
	 */
	public static String objectToXml(RetailProposalRegisterResponse object) {

		XStream xStream = new XStream();

		xStream.processAnnotations(RetailProposalRegisterResponse.class);

		return xStream.toXML(object);
	}

	/**
	 * M�todo para de-serializar um XML.
	 * 
	 * @param xmlString
	 *            String com o XML a ser de-serializado.
	 * @return Objeto representando o XML de-serializado.
	 */
	public static RetailProposalRegisterResponse xmlToObject(String xmlString) {

		XStream xStream = new XStream();

		xStream.processAnnotations(RetailProposalRegisterResponse.class);

		return (RetailProposalRegisterResponse) xStream.fromXML(xmlString);
	}
}
