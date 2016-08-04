package com.jeff.shareapp.util;

/**
 * Created by 张武 on 2016/5/19.
 */
public class StaticFlag {


    //用户类型
    public final static int STUDENT_USER_TYPE = 1;
    public final static int TEACHER_USER_TYPE = 0;


    //测试题类型 0-选择题  1-问答题
    public final static int QUESTION_SELECT = 0;
    public final static int QUESTION_TEXT = 1;

    //资源文件类型
    public final static int FILE_TYPE_PIC = 0;
    public final static int FILE_TYPE_TEXT = 1;
    public final static int FILE_TYPE_PPT = 2;
    public final static int FILE_TYPE_AUDIO = 3;
    public final static int FILE_TYPE_VEDIO = 4;
    public final static int FILE_TYPE_OTHER = 5;

    //mainactivity 所附fragment
    public final static int INDEXPAGE_FRAGMENT = 101;
    public final static int ALLPAGE_FRAGMENT = 102;
    public final static int TASKPAGE_FRAGMENT = 103;
    public final static int MYPAGE_FRAGMENT = 104;

    //网络请求结果
    public final static int SUCCESS = 1;
    public final static int FAILURE = 0;
    public final static int ERROR = -1;
    //文件上传结果
    public final static int UPLOAD_START = 10;
    public final static int UPLOAD_UPLOADING = 11;
    public final static int UPLOAD_SUCCESS = 12;
    public final static int UPLOAD_FAIL = -11;

    //token过期代码
    public final static int TOKEN_EXPIRE = 440;

    //服务器地址

    //家里
//    public final static String HOST = "http://192.168.31.105:8080";
 //笔记本放wifi
 //   public final static String HOST = "http://192.168.191.1:8080";
//公司
    public final static String HOST = "http://10.106.138.22:8080";

    public final static String SERVICE = HOST + "/shareApp/";

    //各接口访问地址

    //文件上传

    public final static String UPLOAD = SERVICE + "upload.do";
    public final static String UPLOAD_IMAGE = SERVICE + "upload_image.do";
    //文件加载地址
    public final static String FILE_URL = HOST + "/upload/";


    //获取主页数据
    public final static String GET_NEWS_PICTURES_LIST = SERVICE + "get_news_pictures_list.do";

    //获取新任务数量接口
    public final static String GET_STUDENT_TASK_NEW = SERVICE + "get_student_task_new.do";

    //用户信息接口
    public final static String LOGIN = SERVICE + "login.do";
    public final static String AUTO_LOGIN = SERVICE + "auto_login.do";
    public final static String REGISTER = SERVICE + "register.do";
    public final static String CHECK_NAME_PHONE = SERVICE + "check_name_phone.do";
    public final static String CHANGE_PASSWORD = SERVICE + "change_password.do";
    public final static String LOGOUT = SERVICE + "logout.do";

    public final static String UPLOAD_IMAGE_URL = SERVICE + "change_headImg.do";
    public final static String GET_INDEX_COURSE = SERVICE + "get_index_course.do";
    public final static String GET_HOT_RESOUSE = SERVICE + "get_hot_resource.do";

    //资源
    public final static String UPLOAD_RESOURCE = SERVICE + "upload_resource.do";
    public final static String GET_RESOURCE_LIST = SERVICE + "get_resource_list.do";
    public final static String COLLECT = SERVICE + "collect.do";
    public final static String GET_UPLOAD_LIST = SERVICE + "get_upload_list.do";
    public final static String GET_RESOURCE_DETIAL = SERVICE + "get_resource_info.do";

    //新建任务
    public final static String ADD_QUESTION = SERVICE + "add_question.do";
    public final static String ARRANGEMENT_WORK = SERVICE + "arrangement_work.do";
    public final static String ADD_TASK = SERVICE + "add_task.do";
    public final static String FIND_STUDENT = SERVICE + "find_student.do";
    public final static String ADD_STUDENT = SERVICE + "add_student.do";
    public final static String GET_ALL_COURSE = SERVICE + "get_all_course.do";
    public final static String TEACHER_GET_TASK_LIST=SERVICE+"get_teacher_task_list.do";
    public final static String STUDENT_GET_TASK_LIST=SERVICE+"get_student_task_list.do";


}
