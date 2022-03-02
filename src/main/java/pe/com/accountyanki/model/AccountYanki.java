package pe.com.accountyanki.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import reactor.core.publisher.Flux;

@Data
@AllArgsConstructor
public class AccountYanki implements Serializable{
	
 private static final long serialVersionUID = 1L;
 
	@Id
	private String id;
	private String numberAccount;
	private double availableBalanceAccount;
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date dateCreationAccount;
	private String statusAccount;
	private String idCard;


}
