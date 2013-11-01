package br.com.ziben.services.client.response;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("RetailProposalRegister")
public class RetailProposalRegisterResponse {
	/**
	 * @Description Contract (contrato) – string(9) código do contrato.
	 *              Parâmetro obrigatório
	 */
	private String Contract; // 999999999

	/**
	 * @Description AnalysisStatus (statusAnalise) – string(1) 0 – Negado, 1 –
	 *              Aprovado ou 2 – Mesa. Parâmetro obrigatório
	 */
	private String AnalysisStatus;

	/**
	 * @Description AuthorizationCode (codAutorizacao) – string(10) Código de
	 *              autorização obtido da financeira. Parâmetro opcional pode
	 *              ser vazio caso o financiamento seja da própria eletrozema)
	 */
	private String AuthorizationCode;

	/**
	 * @Description OrderNumber (numeroPedido) – string(10) identificador do
	 *              orçamento no Protheus. Parâmetro obrigatório
	 */
	private String OrderNumber;

	/**
	 * @Description PurchaseAmount (valorCompra) – number(14,5) valor da compra
	 *              do contrato pela financeira. Se a própria Eletrozema
	 *              financiar o contrato então será 0.
	 */
	private String PurchaseAmount;

	/**
	 * @Description Return (retorno) – string(1) 0 indica erro, 1 sucesso.
	 *              Parâmetro obrigatório
	 */
	private String Return; // 0

	/**
	 * @Description Message1 (mensagem1) – string(255) mensagem real de erro
	 *              caso ocorra. Parâmetro obrigatório
	 */
	private String Message1; // XXXXXXXXX

	/**
	 * @Description Message2 (mensagem2) – string(255) mensagem amigável para
	 *              exibição ao usuario. Parâmetro obrigatório
	 */
	private String Message2; // XXXXXXXXX

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
	 * @param response
	 * @return String xml
	 */
	public static String objectToXml(RetailProposalRegisterResponse response) {

		XStream xStream = new XStream();

		xStream.processAnnotations(RetailProposalRegisterResponse.class);

		return xStream.toXML(response);
	}

	/**
	 * @param response
	 * @return String xml
	 */
	public static RetailProposalRegisterResponse xmlToObject(String response) {

		XStream xStream = new XStream();

		xStream.processAnnotations(RetailProposalRegisterResponse.class);

		return (RetailProposalRegisterResponse) xStream.fromXML(response);
	}

}
