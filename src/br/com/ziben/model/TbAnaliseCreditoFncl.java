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

package br.com.ziben.model;

// Generated Oct 30, 2013 12:33:40 PM by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TbAnaliseCreditoFncl generated by hbm2java
 */
@Entity
@Table(name = "tb_analise_credito_fncl", schema = "public")
public class TbAnaliseCreditoFncl implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3194437849913663959L;
	private TbAnaliseCreditoFnclId id;
	private Boolean tipoped;
	private Integer numpedven;
	private Date dtultmov;
	private Date hrultmov;
	private Boolean status;
	private Date dtlancto;
	private String segUsuarioCpf;
	private long codcli;
	private BigDecimal valor;
	private Boolean statusEnvio;
	private String detalhes;
	private Date dtinclusao;
	private Date hrinclusao;
	private Date dtinclusaoFilial;
	private Date hrinclusaoFilial;
	private Date dtanalise;
	private Date hranalise;
	private Long idPedidoCartao;
	private String nomcli;
	private Long cgccpf;
	private String xmllog;
	private String condpgto;

	public TbAnaliseCreditoFncl() {
	}

	public TbAnaliseCreditoFncl(TbAnaliseCreditoFnclId id, Date dtultmov, Date hrultmov, Date dtlancto, String segUsuarioCpf, long codcli) {
		this.id = id;
		this.dtultmov = dtultmov;
		this.hrultmov = hrultmov;
		this.dtlancto = dtlancto;
		this.segUsuarioCpf = segUsuarioCpf;
		this.codcli = codcli;
	}

	public TbAnaliseCreditoFncl(TbAnaliseCreditoFnclId id, Boolean tipoped, Integer numpedven, Date dtultmov, Date hrultmov, Boolean status, Date dtlancto,
			String segUsuarioCpf, long codcli, BigDecimal valor, Boolean statusEnvio, String detalhes, Date dtinclusao, Date hrinclusao, Date dtinclusaoFilial,
			Date hrinclusaoFilial, Date dtanalise, Date hranalise, Long idPedidoCartao, String nomcli, Long cgccpf, String xmllog, String condpgto) {
		this.id = id;
		this.tipoped = tipoped;
		this.numpedven = numpedven;
		this.dtultmov = dtultmov;
		this.hrultmov = hrultmov;
		this.status = status;
		this.dtlancto = dtlancto;
		this.segUsuarioCpf = segUsuarioCpf;
		this.codcli = codcli;
		this.valor = valor;
		this.statusEnvio = statusEnvio;
		this.detalhes = detalhes;
		this.dtinclusao = dtinclusao;
		this.hrinclusao = hrinclusao;
		this.dtinclusaoFilial = dtinclusaoFilial;
		this.hrinclusaoFilial = hrinclusaoFilial;
		this.dtanalise = dtanalise;
		this.hranalise = hranalise;
		this.idPedidoCartao = idPedidoCartao;
		this.nomcli = nomcli;
		this.cgccpf = cgccpf;
		this.xmllog = xmllog;
		this.condpgto = condpgto;
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "codfil", column = @Column(name = "codfil", nullable = false, precision = 3, scale = 0)),
			@AttributeOverride(name = "idanalise", column = @Column(name = "idanalise", nullable = false, precision = 9, scale = 0)),
			@AttributeOverride(name = "idproduto", column = @Column(name = "idproduto", nullable = false, precision = 3, scale = 0)) })
	public TbAnaliseCreditoFnclId getId() {
		return this.id;
	}

	public void setId(TbAnaliseCreditoFnclId id) {
		this.id = id;
	}

	@Column(name = "tipoped", precision = 1, scale = 0)
	public Boolean getTipoped() {
		return this.tipoped;
	}

	public void setTipoped(Boolean tipoped) {
		this.tipoped = tipoped;
	}

	@Column(name = "numpedven", precision = 9, scale = 0)
	public Integer getNumpedven() {
		return this.numpedven;
	}

	public void setNumpedven(Integer numpedven) {
		this.numpedven = numpedven;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtultmov", nullable = false, length = 29)
	public Date getDtultmov() {
		return this.dtultmov;
	}

	public void setDtultmov(Date dtultmov) {
		this.dtultmov = dtultmov;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "hrultmov", nullable = false, length = 29)
	public Date getHrultmov() {
		return this.hrultmov;
	}

	public void setHrultmov(Date hrultmov) {
		this.hrultmov = hrultmov;
	}

	@Column(name = "status", precision = 1, scale = 0)
	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtlancto", nullable = false, length = 29)
	public Date getDtlancto() {
		return this.dtlancto;
	}

	public void setDtlancto(Date dtlancto) {
		this.dtlancto = dtlancto;
	}

	@Column(name = "seg_usuario_cpf", nullable = false, length = 11)
	public String getSegUsuarioCpf() {
		return this.segUsuarioCpf;
	}

	public void setSegUsuarioCpf(String segUsuarioCpf) {
		this.segUsuarioCpf = segUsuarioCpf;
	}

	@Column(name = "codcli", nullable = false, precision = 15, scale = 0)
	public long getCodcli() {
		return this.codcli;
	}

	public void setCodcli(long codcli) {
		this.codcli = codcli;
	}

	@Column(name = "valor", precision = 15)
	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Column(name = "status_envio", precision = 1, scale = 0)
	public Boolean getStatusEnvio() {
		return this.statusEnvio;
	}

	public void setStatusEnvio(Boolean statusEnvio) {
		this.statusEnvio = statusEnvio;
	}

	@Column(name = "detalhes")
	public String getDetalhes() {
		return this.detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtinclusao", length = 29)
	public Date getDtinclusao() {
		return this.dtinclusao;
	}

	public void setDtinclusao(Date dtinclusao) {
		this.dtinclusao = dtinclusao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "hrinclusao", length = 29)
	public Date getHrinclusao() {
		return this.hrinclusao;
	}

	public void setHrinclusao(Date hrinclusao) {
		this.hrinclusao = hrinclusao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtinclusao_filial", length = 29)
	public Date getDtinclusaoFilial() {
		return this.dtinclusaoFilial;
	}

	public void setDtinclusaoFilial(Date dtinclusaoFilial) {
		this.dtinclusaoFilial = dtinclusaoFilial;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "hrinclusao_filial", length = 29)
	public Date getHrinclusaoFilial() {
		return this.hrinclusaoFilial;
	}

	public void setHrinclusaoFilial(Date hrinclusaoFilial) {
		this.hrinclusaoFilial = hrinclusaoFilial;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtanalise", length = 29)
	public Date getDtanalise() {
		return this.dtanalise;
	}

	public void setDtanalise(Date dtanalise) {
		this.dtanalise = dtanalise;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "hranalise", length = 29)
	public Date getHranalise() {
		return this.hranalise;
	}

	public void setHranalise(Date hranalise) {
		this.hranalise = hranalise;
	}

	@Column(name = "id_pedido_cartao", precision = 15, scale = 0)
	public Long getIdPedidoCartao() {
		return this.idPedidoCartao;
	}

	public void setIdPedidoCartao(Long idPedidoCartao) {
		this.idPedidoCartao = idPedidoCartao;
	}

	@Column(name = "nomcli", length = 40)
	public String getNomcli() {
		return this.nomcli;
	}

	public void setNomcli(String nomcli) {
		this.nomcli = nomcli;
	}

	@Column(name = "cgccpf", precision = 15, scale = 0)
	public Long getCgccpf() {
		return this.cgccpf;
	}

	public void setCgccpf(Long cgccpf) {
		this.cgccpf = cgccpf;
	}

	@Column(name = "xmllog", length = 8000)
	public String getXmllog() {
		return this.xmllog;
	}

	public void setXmllog(String xmllog) {
		this.xmllog = xmllog;
	}

	@Column(name = "condpgto", length = 3)
	public String getCondpgto() {
		return this.condpgto;
	}

	public void setCondpgto(String condpgto) {
		this.condpgto = condpgto;
	}

}
