package com.paril.mlaclientapp.webservice;

import com.paril.mlaclientapp.model.FriendEncyModel;
import com.paril.mlaclientapp.model.GroupModel;
import com.paril.mlaclientapp.model.MLAAdminDetails;
import com.paril.mlaclientapp.model.FriendModel;
import com.paril.mlaclientapp.model.MLAFriendRequest;
import com.paril.mlaclientapp.model.MLAGradeTask;
import com.paril.mlaclientapp.model.GDModel;
import com.paril.mlaclientapp.model.MLAInstructorDetails;
import com.paril.mlaclientapp.model.MLALeaveGroup;
import com.paril.mlaclientapp.model.PModel;
import com.paril.mlaclientapp.model.MLARegisterUsers;
import com.paril.mlaclientapp.model.MLAScheduleDetailPostData;
import com.paril.mlaclientapp.model.MLAStudentDetails;
import com.paril.mlaclientapp.model.MLAStudentEnrollmentPostData;
import com.paril.mlaclientapp.model.MLASubjectDetails;
import com.paril.mlaclientapp.model.MLATaskDetails;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("api/Register/GetRegisterAuth")
    Call<List<MLARegisterUsers>> authenticate(@Query("userName") String userName, @Query("password") String password);

    @GET("api/Admin/GetAdminByUserName")
    Call<List<MLAAdminDetails>> getAdminInfo(@Query("userName") String userName);


    @GET("api/Student/GetStudentByUserName")
    Call<List<MLAStudentDetails>> getStudentInfo(@Query("userName") String userName);


    @GET("api/Instructor/GetInstructorByUserName")
    Call<List<MLAInstructorDetails>> getInstInfo(@Query("userName") String userName);


    @GET("api/Admin/GetAllAdmin")
    Call<List<MLAAdminDetails>> getAdminUsers();

    @POST("api/DeleteAdmin/PostAdminRmv")
    Call<String> removeAdmin(@Query("idAdminRmv") String adminUserName);

    @POST("api/Register/PostAddAdmin")
    Call<String> addAdmin(@Query("adminUserName") String adminUserName, @Query("adminPassword") String adminPassword, @Query("adminFirsName") String adminFirsName, @Query("adminLastName") String adminLastName, @Query("adminTelephone") String adminTelephone, @Query("adminAddress") String adminAddress, @Query("adminAliasMailId") String adminAliasMailId, @Query("adminEmailId") String adminEmailId, @Query("adminSkypeId") String adminSkypeId);

    @POST("api/Admin/PostAdminUpdate")
    Call<String> updateAdmin(@Body MLAAdminDetails userDetails);


    @GET("api/Instructor/GetAllInstructor")
    Call<List<MLAInstructorDetails>> getInstructors();

    @POST("api/DeleteInstructor/PostInstructorRmv")
    Call<String> removeInstructor(@Query("idInstructorRmv") String idInstructorRmv);

    @POST("api/Register/PostAddInstructor")
    Call<MLAInstructorDetails> addInst(@Query("instUserName") String userName, @Query("instPassword") String password, @Query("instFirsName") String instFirsName, @Query("instLastName") String instLastName, @Query("instTelephone") String instTelephone, @Query("instAddress") String instAddress, @Query("instAliasMailId") String instAliasMailId, @Query("instEmailId") String instEmailId, @Query("instSkypeId") String instSkypeId);

    @POST("api/Instructor/PostInstructorUpdate/")
    Call<String> updateInstructor(@Body MLAInstructorDetails userDetails);


    @GET("api/Student/GetAllStudent")
    Call<List<MLAStudentDetails>> getStudents();

    @GET("api/Subject/GetSubjectByStudent")
    Call<ArrayList<MLASubjectDetails>> getSubjForStudent(@Query("idStudent") String idStudent);

    @POST("api/DeleteStudent/PostStudentRmv")
    Call<String> removeStudent(@Query("idStudentRmv") String idInstructorRmv);

    @POST("api/Register/PostAddedofStudent")
    Call<String> addStudent(@Query("userName") String userName, @Query("password") String password, @Query("firstName") String instFirsName, @Query("lastName") String instLastName, @Query("telephone") String instTelephone, @Query("address") String instAddress, @Query("aliasMailId") String instAliasMailId, @Query("emailId") String instEmailId, @Query("skypeId") String instSkypeId);

    @POST("api/Student/PostStudentUpdate/")
    Call<String> updateStudent(@Body MLAStudentDetails userDetails);

    @POST("api/Register/PostRegisterPassUpdate")
    Call<String> changePassword(@Query("userName") String userName, @Query("password") String password);

    @GET("api/Subject/GetAllSubject")
    Call<List<MLASubjectDetails>> getAllSubject();


    @GET("api/Subject/GetAllSubjectWithTask")
    Call<List<MLASubjectDetails>> getAllSubjectWithTask(@Query("flag") String subjectId);

    @POST("api/Subject/PostSubject")
    Call<MLASubjectDetails> addSubject(@Body MLASubjectDetails subjectDetails);

    @POST("api/SubjectUpdate/PostSubjectUpdate")
    Call<String> updateSubject(@Body MLASubjectDetails subjectDetails);

    @POST("api/SubjectRmv/PostSubjectRmv")
    Call<String> removeSubject(@Query("subject_id") String idSubject);

    @GET("api/DeEnrollStudent/GetDeEnrollBySubject")
    Call<List<MLAStudentDetails>> getDeEnrollBySub(@Query("idSubject") String subjectId);


    @GET("api/EnrollStudent/GetEnrollBySubject")
    Call<List<MLAStudentDetails>> getEnrollBySub(@Query("idSubject") String subjectId);

    @POST("api/EnrollStudent/PostEnrollStudent")
    Call<MLAStudentEnrollmentPostData> enrollBySub(@Body MLAStudentEnrollmentPostData listStudentData);


    @POST("api/DeEnrollStudent/PostDeEnroll")
    Call<MLAStudentEnrollmentPostData> deEnrollBySub(@Body MLAStudentEnrollmentPostData listStudentData);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("api/EnrollStudent/GetSubjectByStd")
    Call<ArrayList<MLASubjectDetails>> getEnrolledSubjectForStudent(@Query("idStudent") String idStudent);

    @POST("api/Tasks/PostTask/")
    Call<String> addSchedule(@Body MLAScheduleDetailPostData details);


    @POST("api/ScheduleRmv/PostTaskRmv")
    Call<String> removeTasks(@Query("subject_id") String subjectId);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("api/Tasks/PostTaskUpdate")
    Call<String> updateTaskData(@Query("idTask") String idTask, @Query("topic") String topic, @Query("description") String desc);


    @GET("api/UserTasks/GetTasksByUser")
    Call<List<MLATaskDetails>> getTasksByUser(@Query("userId") String userName, @Query("userType") String userType);


    @GET("api/Tasks/GetTasksBySubject")
    Call<List<MLATaskDetails>> getTasksBySubject(@Query("subjectId") String subjectId);

    @GET("api/Tasks/GetStudentByTask")
    Call<List<MLAGradeTask>> getGrades(@Query("task") String task, @Query("subjectid") String subjId);

    @GET("api/Tasks/GetTasksByStudent")
    Call<List<MLATaskDetails>> getListTaskForStudent(@Query("subject") String subject, @Query("studentId") String studentId);


    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("api/Tasks/PostGradeUpdate")
    Call<String> updateGrade(@Query("task_id") String taskId, @Query("student_id") String student_id, @Query("grade") String grade);

    @GET("api/Tasks/GetTasksByStudent")
    Call<List<MLAGradeTask>> getGradesForStudent(@Query("studentId") String studentId, @Query("subject") String subject);


    // Add PK
    @POST("api/Register/AddPublickey")
    Call<String> addPk(@Query("userId") Integer userId,@Query("groupKeyDef") String groupKey,@Query("publicKey") String publicKey,@Query("privategroupKey") String privategroupKey);

//  Post APIs
    @GET("api/Post/GetAllPost")
    Call<List<PModel>> getAllPosts(@Query("userId") Integer userId);
//
    @POST("api/Post/AddPost")
    Call<String>addPost(@Body PModel post);

    // to get the grps for post page
    @GET("api/Post/GetGroupListforPost")
    Call<List<GDModel>> getGroupForPost(@Query("groupUserId") Integer groupUserId, @Query("grouptype") Integer  grouptype, @Query("groupId") Integer groupId);


    @GET("api/ManageFriends/GetFriendforReq")
    Call<List<FriendModel>> notinFriendList(@Query("currentUserId") Integer currentUserId);

    @POST("api/ManageFriends/AddAsFriend")
    Call<Integer> addFriend(@Body FriendEncyModel listofFriends);

    @GET("api/ManageFriends/GetRequestedFriendList")
    Call<List<MLAFriendRequest>> gettherequesteddata(@Query("userId") Integer userId);

    @POST("api/ManageFriends/AcceptFriendRequest")
     Call<String> acptRequest(@Query("userId") Integer userId, @Query("friendId") Integer friendId, @Query("groupKey") String groupkey, @Query("groupId") String groupId);

    @POST("api/ManageFriends/RejectFriendrequest")
    Call<String> rejRequest(@Query("userId") Integer userId, @Query("fromuserId") Integer fromuserId);


     // Group APIs
    @GET("api/ManageGroup/GetTheFriendsToAddInGroup")
    Call<List<GroupModel>> getListofFriendsToaddInGroup(@Query("userId") Integer userId);

    @POST("api/ManageGroup/CreateANewGroup")
    Call<Integer> addGroup(@Body GroupModel listOfGroup);


    @POST("api/ManageGroup/CreateANewGroupWithFriends")
    Call<Integer> addGroupWithFriends(@Body GroupModel listOfGroup);


    // For adding more friends to existing groups
    @GET("api/ManageGroup/GetGroupListCreatedByUser")
    Call<List<GroupModel>> getlistofGroups(@Query("groupUserId") Integer groupUserId);

    // for getting the list of friends to add in current grp
    @GET("api/ManageGroup/GetFriendListNotIngroup")
    Call<List<GroupModel>> getlistofFriendsforUpdate(@Query("groupId") Integer groupId, @Query("userId") Integer userId);

    @GET("api/ManageGroup/GetGroupListByUser")
    Call<List<MLALeaveGroup>>getGroupDetails (@Query("groupUserId") Integer groupUserId);

    @POST("api/ManageGroup/GroupDel")
    Call<String> removeGrp (@Query("groupId") Integer groupId);


}