package br.com.ziben.test;

import br.com.ziben.services.client.response.RetailProposalRegisterResponse;

public class XmlProcessTest {

	public static void main(String[] args) {

		String xml = "<RetailProposalRegister>"
				+ "<Contract>000000000355184</Contract>"
				+ "<AnalysisStatus>2</AnalysisStatus>"
				+ "<AuthorizationCode></AuthorizationCode>"
				+ "<OrderNumber>10577</OrderNumber><PurchaseAmount>216.01</PurchaseAmount><Return>0</Return>"
				+ "<Message1>Cliente comprou recentemente na Zema /Excesso de passagens no CDL</Message1>"
				+ "<Message2>Pedido enviado para a mesa de crédito</Message2>"
				+ "</RetailProposalRegister>";

		RetailProposalRegisterResponse retailProposalRegisterResponse = RetailProposalRegisterResponse.xmlToObject(xml);

		System.out.println("AnalysisStatus: " + retailProposalRegisterResponse.getAnalysisStatus());
		System.out.println("AuthorizationCode: " + retailProposalRegisterResponse.getAuthorizationCode());
		System.out.println("Contract: " + retailProposalRegisterResponse.getContract());
		System.out.println("Message1: " + retailProposalRegisterResponse.getMessage1());
		System.out.println("Message2: " + retailProposalRegisterResponse.getMessage2());
		System.out.println("OrderNumber: " + retailProposalRegisterResponse.getOrderNumber());
		System.out.println("PurchaseAmount: " + retailProposalRegisterResponse.getPurchaseAmount());
		System.out.println("Return: " + retailProposalRegisterResponse.getReturn());
	}
}
