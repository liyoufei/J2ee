import com.sss.Mapper.BeanMapper;
import com.sss.action.LoginAction;
import com.sss.bean.BookBean;
import com.sss.bean.UserBean;
import com.sss.config.ParseDi;
import com.sss.config.ParseOR;
import com.sss.dao.Conversation;
import com.sss.utility.DiUtility;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        ArrayList<BeanMapper> beanMappers = new ParseDi("D:\\project\\J2ee\\untitled\\src\\di.xml").getBeanMappers();
        LoginAction loginAction = new LoginAction();
        loginAction =(LoginAction) DiUtility.handleDi(loginAction,beanMappers);

    }
}
