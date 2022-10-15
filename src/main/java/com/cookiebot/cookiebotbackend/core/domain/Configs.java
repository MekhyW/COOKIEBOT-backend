package com.cookiebot.cookiebotbackend.core.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Configs implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private Boolean furbots;
	private String sticker_spam_limit;
	private Integer tempo_sem_poder_mandar_mensagem;
	private Integer tempo_captcha;
	private Boolean funcoes_diversao;
	private Boolean funcoes_utilidade;
	private Boolean sfw;
	private String language;
	
	public Configs() {	
	}

	public Configs(String id, Boolean furbots, String sticker_spam_limit, Integer tempo_sem_poder_mandar_mensagem,
			Integer tempo_captcha, Boolean funcoes_diversao, Boolean funcoes_utilidade, Boolean sfw, String language) {
		super();
		this.id = id;
		this.furbots = furbots;
		this.sticker_spam_limit = sticker_spam_limit;
		this.tempo_sem_poder_mandar_mensagem = tempo_sem_poder_mandar_mensagem;
		this.tempo_captcha = tempo_captcha;
		this.funcoes_diversao = funcoes_diversao;
		this.funcoes_utilidade = funcoes_utilidade;
		this.sfw = sfw;
		this.language = language;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getFurbots() {
		return furbots;
	}

	public void setFurbots(Boolean furbots) {
		this.furbots = furbots;
	}

	public String getSticker_spam_limit() {
		return sticker_spam_limit;
	}

	public void setSticker_spam_limit(String sticker_spam_limit) {
		this.sticker_spam_limit = sticker_spam_limit;
	}

	public Integer getTempo_sem_poder_mandar_mensagem() {
		return tempo_sem_poder_mandar_mensagem;
	}

	public void setTempo_sem_poder_mandar_mensagem(Integer tempo_sem_poder_mandar_mensagem) {
		this.tempo_sem_poder_mandar_mensagem = tempo_sem_poder_mandar_mensagem;
	}

	public Integer getTempo_captcha() {
		return tempo_captcha;
	}

	public void setTempo_captcha(Integer tempo_captcha) {
		this.tempo_captcha = tempo_captcha;
	}

	public Boolean getFuncoes_diversao() {
		return funcoes_diversao;
	}

	public void setFuncoes_diversao(Boolean funcoes_diversao) {
		this.funcoes_diversao = funcoes_diversao;
	}

	public Boolean getFuncoes_utilidade() {
		return funcoes_utilidade;
	}

	public void setFuncoes_utilidade(Boolean funcoes_utilidade) {
		this.funcoes_utilidade = funcoes_utilidade;
	}

	public Boolean getSfw() {
		return sfw;
	}

	public void setSfw(Boolean sfw) {
		this.sfw = sfw;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
