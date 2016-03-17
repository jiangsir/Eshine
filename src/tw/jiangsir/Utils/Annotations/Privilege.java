package tw.jiangsir.Utils.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User.ROLE 會決定 user 的基本權限。<br/>
 * 這裡用來處理 user 進入不同的狀態時，在此設定權限的增加、或減少。<br/>
 * 
 * 1. ProblemManager<br>
 * 2. GeneralManager<br>
 * 3. VClassManager<br>
 * 4. UserInContest: User 進入 Contest 會減少的權限。<br>
 * 
 * @author jiangsir
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Privilege {
}
