import { useEffect, useState } from "react";
import { Context } from "../Context";
import { getAccountById, updateAccount } from "../../api/accountApi";
import { type Account } from "../../types/Account";
import type { UpdateAccountDTO } from "../../types/updateAccountDTO";

export default function ProfileUserComponent(){

    const { loggedId, accountType } = Context(); //get authentication data(id of the logged in user)

    const [profile, setProfile] = useState<Partial<Account>>({}); //partial acc good for editable forms
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

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {//It contains everything about the change that just happened.

        if (!profile) return;

        setProfile({ //setProfile updates react state
            ...profile,
            [e.target.name]: e.target.value //e.target is the input element itself that triggered the change.
        });
    }

    const handleSave = async () => { //calls updateAccount

        if (!loggedId) return;
        await updateAccount(loggedId,  profile as UpdateAccountDTO);

        setIsEditing(false);
    };

};
