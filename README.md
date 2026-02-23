# career-tracker
Career Tracker is a full-stack web app that helps users organize and monitor their job applications. It lets users record roles, companies, statuses, and notes, and review their activity using a clear, simple interface powered by a Springboot API, PostgreSQL, Redis(for caching) and React for frontend. Docker will be used for deployment.
We plan using Elastic search in the future to make the application more user-friendly.


# COMMANDS

./gradlew bootRun
./gradlew clean build
linux: export $(cat .env | xargs)

windows:
Get-Content .env | ForEach-Object {
    if ($_ -and $_ -notmatch '^#') {
        $name, $value = $_ -split '=', 2
        Set-Item -Path "Env:$name" -Value $value
    }
}

docker buildx build --platform linux/amd64 -t loca0307/careertracker --push .

##  Features

### Accounts Management
Companies can create dedicated accounts to publish job posts, providing detailed information about open positions, required skills, responsibilities, and application deadlines.
The platform is designed to help organizations efficiently reach candidates who are actively searching for new opportunities.
On the other hand, users can register, create personal profiles.The application process is simple and intuitive, allowing users to track the status of their applications and manage multiple submissions in one place.
Additionally, the platform facilitates interaction between companies and candidates by enabling companies to review applicant profiles.

###  Job Application Management
- Create a job application  
- View list of applications  
- View details of a single application  
- Update application (status, notes, etc.)  
- Delete application  

Each application stores:
- Company  
- Role / Position  
- Application date  
- Status (Applied, Interview, Offer, Rejected, etc.)  
- Notes  


## Project Goals

This project is designed for:
- Learning full-stack development with modern tools  
- Practicing clean architecture and REST API design  

---

## Run the project

./gradlew bootRun
