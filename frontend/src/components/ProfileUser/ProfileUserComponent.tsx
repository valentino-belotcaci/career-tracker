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


    const handleSave = async () => { //calls updateAccount

        if (!loggedId) 
            return;
        try {

            await updateAccount(loggedId, profile as UpdateAccountDTO);
            setIsEditing(false);
            alert("Profile updated successfully!");

        } catch (err) {

            console.error("Failed to update profile:", err);
            alert("Failed to save profile. Try again.");
        }
    };

    //show loading while id is null
    if (!loggedId || Object.keys(profile).length === 0) return <p>Loading...</p>;


    
}