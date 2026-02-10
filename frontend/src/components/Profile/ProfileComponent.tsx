import { useEffect, useState } from "react";
import { Context } from "../Context";
import { getAccountById, updateAccount } from "../../api/accountApi";
import { type Account, type User, type Company } from "../../types/Account";
import type { UpdateAccountDTO } from "../../types/updateAccountDTO";

//combine optional fields for User and Company
type ProfileState = Partial<User> & Partial<Company>;

export default function ProfileComponent(){

    const { loggedId, accountType } = Context(); //get authentication data(id of the logged in user)

    const [profile, setProfile] = useState<ProfileState>({}); //partial acc good for editable forms
    //profile holds user data, set profile to update it when we clicke edit
    const [isEditing, setIsEditing] = useState(false); //false view mode, true edit mode

    useEffect(() => { //we fetch the data after the component renders
        
        const fetchProfile = async () => {

            if (!loggedId) 
                return; //at this point its defenitely a string, if  not we go back
            
            try {

                const data = await getAccountById(loggedId);
                setProfile(data);

            } catch (err) {

                console.error("Failed to fetch profile:", err);
            }
    };

    fetchProfile();

    }, [loggedId]);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {//It contains everything about the change that just happened.

        if (!profile) return;

        setProfile({ //setProfile updates react state
            ...profile,
            [e.target.name]: e.target.value //e.target is the input element itself that triggered the change.
        });
    }


    const handleSave = async () => {
    if (!loggedId) return;

    try {
        // Build DTO properly
        const dto: UpdateAccountDTO = {
            email: profile.email || "",
            description: profile.description || "",
            firstName: accountType === "USER" ? profile.firstName || "" : "",
            lastName: accountType === "USER" ? profile.lastName || "" : "",
            companyName: accountType === "COMPANY" ? profile.companyName || "" : "",
            city: accountType === "COMPANY" ? profile.city || "" : "",
            street: accountType === "COMPANY" ? profile.street || "" : "",
            number: accountType === "COMPANY" ? profile.number || "" : "",
        };

        await updateAccount(loggedId, dto);
        setIsEditing(false);
    } catch (err) {
        console.error("Failed to update profile:", err);
    }
};


    //show loading while profile id is null
    if (!loggedId || Object.keys(profile).length === 0) return <p>Loading...</p>;


    return (
    <div style={{ maxWidth: 600, margin: "0 auto" }}>
      <h1>{accountType === "USER" ? "User Profile" : "Company Profile"}</h1>

      {/* COMMON FIELDS */}
      <div>
        <label>Email:</label>
        {isEditing ? (
          <input name="email" value={profile.email || ""} onChange={handleChange} />
        ) : (
          <p>{profile.email}</p>
        )}
      </div>

      <div>
        <label>Description:</label>
        {isEditing ? (
          <input name="description" value={profile.description || ""} onChange={handleChange} />
        ) : (
          <p>{profile.description}</p>
        )}
      </div>

      {/* USER-SPECIFIC FIELDS */}
      {accountType === "USER" && (
        <>
          <div>
            <label>First Name:</label>
            {isEditing ? (
              <input name="firstName" value={profile.firstName || ""} onChange={handleChange} />
            ) : (
              <p>{profile.firstName}</p>
            )}
          </div>

          <div>
            <label>Last Name:</label>
            {isEditing ? (
              <input name="lastName" value={profile.lastName || ""} onChange={handleChange} />
            ) : (
              <p>{profile.lastName}</p>
            )}
          </div>
        </>
      )}

      {/* COMPANY-SPECIFIC FIELDS */}
      {accountType === "COMPANY" && (
        <>
          <div>
            <label>Company Name:</label>
            {isEditing ? (
              <input
                name="companyName"
                value={profile.companyName || ""}
                onChange={handleChange}
              />
            ) : (
              <p>{profile.companyName}</p>
            )}
          </div>

          <div>
            <label>City:</label>
            {isEditing ? (
              <input name="city" value={profile.city || ""} onChange={handleChange} />
            ) : (
              <p>{profile.city}</p>
            )}
          </div>

          <div>
            <label>Street:</label>
            {isEditing ? (
              <input name="street" value={profile.street || ""} onChange={handleChange} />
            ) : (
              <p>{profile.street}</p>
            )}
          </div>

          <div>
            <label>Number:</label>
            {isEditing ? (
              <input name="number" value={profile.number || ""} onChange={handleChange} />
            ) : (
              <p>{profile.number}</p>
            )}
          </div>
        </>
      )}

      {/* Buttons */}
      <div style={{ marginTop: 20 }}>
        {isEditing ? (
          <button onClick={handleSave} style={{ marginRight: 10 }}>
            Save
          </button>
        ) : (
          <button onClick={() => setIsEditing(true)}>Edit</button>
        )}
      </div>
    </div>
  );
}