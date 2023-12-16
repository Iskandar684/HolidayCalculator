import { StatementStatus } from "./StatementStatus";
import { StatementType } from "./StatementType";
import { User } from "./User";

export interface StatementEntry {

    statementType: StatementType;

    createDate: Date;

    considerDate: Date;

    status: StatementStatus;

    consider: User;
}