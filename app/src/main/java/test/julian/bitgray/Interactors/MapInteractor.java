package test.julian.bitgray.Interactors;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import test.julian.bitgray.Asynctask.GetUserList;
import test.julian.bitgray.Models.User;

/**
 * Created by JulianStack on 09/06/2017.
 */

public interface MapInteractor {
    void getUserList(Context context, GetUserList.UserListInterface userListInterface);
}
