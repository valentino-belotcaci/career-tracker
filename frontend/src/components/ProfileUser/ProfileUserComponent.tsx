import { useEffect, useState } from "react";
import { Context } from "../Context";
import { getAccountById, updateAccount } from "../../api/accountApi";
import { type Account } from "../../types/Account";

export default function ProfileUserComponent(){

    const { loggedId } = Context(); //get authentication data(id of the logged in user)

    const [profile, setProfile] = useState<Account | null>(null) //User | null  means, at start null, after fetch become User Object.
    //profile holds user data, set profile to update it when we clicke edit
    const [isEditing, setIsEditing] = useState(false); //false view mode, true edit mode

    useEffect(() => { //we fetch the data after the component renders
        const fetchUser = async () => {

        if (!loggedId) //at this point its defenitely a string, if  not we go back
            return;

        const data = await getAccountById(loggedId);
        setProfile(data);
        };

        if (loggedId) fetchUser();
    }, [loggedId]);

    
}