package br.uff.labtempo.osiris.mapper;

import br.uff.labtempo.osiris.model.domain.UserAccount;
import br.uff.labtempo.osiris.model.request.UserAccountRequest;
import br.uff.labtempo.osiris.model.response.UserAccountResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @see br.uff.labtempo.osiris.model.request.UserAccountRequest
 * @see br.uff.labtempo.osiris.model.domain.UserAccount
 * @see br.uff.labtempo.osiris.model.response.UserAccountResponse
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
public class UserAccountMapper {

    /**
     * Converts UserAccountRequest to UserAccount
     * @see UserAccountRequest
     * @see UserAccount
     * @param userAccountRequest
     * @return UserAccount
     */
    public static UserAccount toUserAccount(UserAccountRequest userAccountRequest) {
        return UserAccount.builder()
                .firstName(userAccountRequest.getFirstName())
                .middleName(userAccountRequest.getMiddleName())
                .lastName(userAccountRequest.getLastName())
                .email(userAccountRequest.getEmail())
                .username(userAccountRequest.getUsername())
                .password(userAccountRequest.getPassword())
                .birthDate(userAccountRequest.getBirthDate())
                .build();
    }

    /**
     * Converts a List of UserAccountRequest to a List of UserAccount
     * @see UserAccountRequest
     * @see UserAccount
     * @param userAccountRequestList
     * @return List of UserAccount
     */
    public static List<UserAccount> toUserAccount(List<UserAccountRequest> userAccountRequestList) {
        List<UserAccount> userAccountList = new ArrayList<>();
        for(UserAccountRequest userAccountRequest : userAccountRequestList) {
            userAccountList.add(toUserAccount(userAccountRequest));
        }
        return userAccountList;
    }

    /**
     * Converts UserAccount to UserAccountResponse
     * @see UserAccount
     * @see UserAccountResponse
     * @param userAccount
     * @return UserAccountResponse
     */
    public static UserAccountResponse toResponse(UserAccount userAccount) {
        return UserAccountResponse.builder()
                .id(userAccount.getId())
                .firstName(userAccount.getFirstName())
                .middleName(userAccount.getMiddleName())
                .lastName(userAccount.getLastName())
                .email(userAccount.getEmail())
                .username(userAccount.getUsername())
                .password(userAccount.getPassword())
                .birthDate(userAccount.getBirthDate())
                .build();
    }

    /**
     * Converts a List of UserAccount to a List of UserAccountResponse
     * @see UserAccount
     * @see UserAccountResponse
     * @param userAccountList
     * @return List of UserAccountResponse
     */
    public static List<UserAccountResponse> toResponse(List<UserAccount> userAccountList) {
        List<UserAccountResponse> userAccountResponseList = new ArrayList<>();
        for(UserAccount userAccount : userAccountList) {
            userAccountResponseList.add(toResponse(userAccount));
        }
        return userAccountResponseList;

    }
}
