package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(name = "article_file_ck", columnNames = {"article_id", "file_id"}
)})
public class ArticleFile extends BaseEntity {

	@OneToOne
	@JoinColumn(name = "article_id", nullable = false)
	private Article article;

	@OneToOne
	@JoinColumn(name = "file_id", nullable = false)
	private File file;

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
