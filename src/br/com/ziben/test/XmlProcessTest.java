/**
	This file is part of adapt-through.

    Adapt-through is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Adapt-through is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Adapt-through.  If not, see <http://www.gnu.org/licenses/>.
 */

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
